package kr.wooco.woocobe.redis.common.stream

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.SmartLifecycle
import org.springframework.data.redis.connection.stream.Consumer
import org.springframework.data.redis.connection.stream.ReadOffset
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.connection.stream.StreamReadOptions
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration
import java.util.concurrent.atomic.AtomicBoolean

class RedisStreamMessagePollingContainer(
    private val redisTemplate: StringRedisTemplate,
    private val streamName: String,
    private val groupName: String,
    private val consumerName: String,
    private val listener: StreamMessageListener,
    private val batchSize: Int = 100,
    private val blockTimeout: Duration = Duration.ofSeconds(2),
) : SmartLifecycle {
    private val running = AtomicBoolean(false)
    private var pollThread: Thread? = null

    override fun start() {
        if (!running.compareAndSet(false, true)) return

        ensureConsumerGroup()
        pollThread = Thread.ofPlatform().name("stream-poll-$streamName").start {
            pollLoop()
        }

        log.info { "[STREAM-POLL] started: stream=$streamName, group=$groupName, consumer=$consumerName" }
    }

    override fun stop() {
        running.set(false)
        log.info { "[STREAM-POLL] stopping: stream=$streamName" }
    }

    override fun isRunning(): Boolean = running.get()

    private fun pollLoop() {
        while (running.get()) {
            try {
                val messages = redisTemplate
                    .opsForStream<String, String>()
                    .read(
                        Consumer.from(groupName, consumerName),
                        StreamReadOptions
                            .empty()
                            .count(batchSize.toLong())
                            .block(blockTimeout),
                        StreamOffset.create(streamName, ReadOffset.lastConsumed()),
                    ) ?: emptyList()

                if (messages.isNotEmpty()) {
                    try {
                        listener.onMessages(messages)
                    } catch (e: Exception) {
                        log.error { "[STREAM-POLL] listener error: ${e.message}" }
                    }
                }
            } catch (_: InterruptedException) {
                Thread.currentThread().interrupt()
                break
            } catch (e: Exception) {
                log.error { "[STREAM-POLL] poll error: ${e.message}" }
                Thread.sleep(1000)
            }
        }
        log.info { "[STREAM-POLL] stopped: stream=$streamName" }
    }

    private fun ensureConsumerGroup() {
        try {
            redisTemplate
                .opsForStream<String, String>()
                .createGroup(streamName, ReadOffset.from("0"), groupName)
            log.info { "[STREAM-POLL] consumer group created: $groupName" }
        } catch (e: Exception) {
            if (e.cause?.message?.contains("BUSYGROUP") == true ||
                e.message?.contains("BUSYGROUP") == true
            ) {
                log.debug { "[STREAM-POLL] consumer group already exists: $groupName" }
            } else {
                throw e
            }
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
