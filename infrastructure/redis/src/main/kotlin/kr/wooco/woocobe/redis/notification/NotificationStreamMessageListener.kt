package kr.wooco.woocobe.redis.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.core.notification.application.port.`in`.SendNotificationUseCase
import kr.wooco.woocobe.redis.common.stream.StreamMessageListener
import kr.wooco.woocobe.redis.common.stream.StreamPendingListener
import kr.wooco.woocobe.redis.common.stream.StreamPendingMessage
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

@Component
class NotificationStreamMessageListener(
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val notificationStreamRedisAdapter: NotificationStreamRedisAdapter,
) : StreamMessageListener,
    StreamPendingListener {
    private val executor = Executors.newVirtualThreadPerTaskExecutor()
    private val semaphore = Semaphore(MAX_CONCURRENT)

    override fun onMessages(messages: List<MapRecord<String, String, String>>) {
        executeInParallel(messages) { processMessage(it) }
    }

    override fun onPendingMessages(messages: List<StreamPendingMessage>) {
        val retryable = messages.filter { pending ->
            if (pending.deliveryCount >= MAX_RETRY) {
                log.error {
                    "[NOTIFICATION-DLQ] dead letter: messageId=${pending.record.id.value}, " +
                        "deliveryCount=${pending.deliveryCount}"
                }
                notificationStreamRedisAdapter.acknowledgeMessage(pending.record.id.value)
                false
            } else {
                true
            }
        }
        if (retryable.isNotEmpty()) {
            executeInParallel(retryable) { processMessage(it.record) }
        }
    }

    private fun <T> executeInParallel(
        items: List<T>,
        action: (T) -> Unit,
    ) {
        val futures = items.map { item ->
            executor.submit {
                semaphore.acquire()
                try {
                    action(item)
                } finally {
                    semaphore.release()
                }
            }
        }

        futures.forEach { it.get() }
    }

    private fun processMessage(message: MapRecord<String, String, String>) {
        val messageId = message.id.value

        val payload = NotificationStreamPayload.from(message)
        if (payload == null) {
            log.warn { "[NOTIFICATION-STREAM] invalid payload, ack: $messageId" }
            notificationStreamRedisAdapter.acknowledgeMessage(messageId)
            return
        }

        try {
            sendNotificationUseCase.send(payload.notificationId)
            notificationStreamRedisAdapter.acknowledgeMessage(messageId)
        } catch (e: Exception) {
            log.error { "[NOTIFICATION-STREAM] failed: notificationId=${payload.notificationId}, reason=${e.message}" }
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
        private const val MAX_CONCURRENT = 50
        private const val MAX_RETRY = 3
    }
}
