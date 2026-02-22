package kr.wooco.woocobe.redis.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.core.notification.domain.event.NotificationCreatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationCreatedEventStreamHandler(
    private val notificationStreamRedisAdapter: NotificationStreamRedisAdapter,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: NotificationCreatedEvent) {
        try {
            notificationStreamRedisAdapter.addToStream(event)
            log.debug { "[NOTIFICATION-STREAM] published: notificationId=${event.aggregateId}" }
        } catch (e: Exception) {
            log.error { "[NOTIFICATION-STREAM] XADD failed: notificationId=${event.aggregateId}, reason=${e.message}" }
        }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
