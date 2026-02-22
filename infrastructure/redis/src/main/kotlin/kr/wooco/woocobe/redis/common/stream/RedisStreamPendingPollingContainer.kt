package kr.wooco.woocobe.redis.common.stream

import io.github.oshai.kotlinlogging.KotlinLogging
import io.lettuce.core.Consumer
import io.lettuce.core.Limit
import io.lettuce.core.Range
import io.lettuce.core.RedisClient
import io.lettuce.core.XPendingArgs
import org.springframework.context.SmartLifecycle
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.stream.RecordId
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

class RedisStreamPendingPollingContainer(
    private val redisTemplate: StringRedisTemplate,
    private val connectionFactory: LettuceConnectionFactory,
    private val streamName: String,
    private val groupName: String,
    private val consumerName: String,
    private val listener: StreamPendingListener,
    private val batchSize: Int = 100,
    private val minIdleTime: Duration = Duration.ofSeconds(30),
    private val pollInterval: Duration = Duration.ofSeconds(5),
) : SmartLifecycle {
    private val running = AtomicBoolean(false)
    private var pollThread: Thread? = null

    override fun start() {
        if (!running.compareAndSet(false, true)) return

        pollThread = Thread.ofPlatform().name("stream-pending-$streamName").start {
            pollLoop()
        }

        log.info { "[STREAM-PENDING] started: stream=$streamName, minIdle=${minIdleTime.seconds}s" }
    }

    override fun stop() {
        running.set(false)
        log.info { "[STREAM-PENDING] stopping: stream=$streamName" }
    }

    override fun isRunning(): Boolean = running.get()

    private fun pollLoop() {
        while (running.get()) {
            try {
                val staleEntries = xpendingWithIdle()

                if (staleEntries.isEmpty()) {
                    Thread.sleep(pollInterval.toMillis())
                    continue
                }

                val deliveryCountMap = staleEntries.associate { it.first to it.second }
                val ids = staleEntries.map { RecordId.of(it.first) }.toTypedArray()

                try {
                    val claimed = redisTemplate
                        .opsForStream<String, String>()
                        .claim(streamName, groupName, consumerName, minIdleTime, *ids)

                    val pendingMessages = claimed.map { record ->
                        StreamPendingMessage(
                            record = record,
                            deliveryCount = deliveryCountMap[record.id.value] ?: 0,
                        )
                    }

                    if (pendingMessages.isNotEmpty()) {
                        listener.onPendingMessages(pendingMessages)
                    }
                } catch (e: Exception) {
                    log.error { "[STREAM-PENDING] claim error: ${e.message}" }
                }
            } catch (_: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            } catch (e: Exception) {
                log.error { "[STREAM-PENDING] poll error: ${e.message}" }
                Thread.sleep(1000)
            }
        }
        log.info { "[STREAM-PENDING] stopped: stream=$streamName" }
    }

    /**
     * XPENDING stream group IDLE minIdleTime - + COUNT batchSize
     * Lettuce native API. Redis 서버에서 idle time 필터링.
     * @return List<Pair<messageId, deliveryCount>>
     */
    private fun xpendingWithIdle(): List<Pair<String, Long>> {
        val client = connectionFactory.nativeClient as RedisClient
        val connection = client.connect()
        connection.use { connection ->
            val commands = connection.sync()
            val args = XPendingArgs<String>()
                .idle(minIdleTime.toMillis())
                .range(Range.unbounded())
                .limit(Limit.from(batchSize.toLong()))
                .consumer(Consumer.from(groupName, consumerName))

            val pendingMessages = commands.xpending(streamName, args)

            return pendingMessages.map { msg ->
                msg.id to msg.redeliveryCount
            }
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
