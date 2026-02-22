package kr.wooco.woocobe.core.notification.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.core.notification.application.port.`in`.SendNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationSenderPort
import org.springframework.stereotype.Service

@Service
class NotificationSendService(
    private val notificationQueryPort: NotificationQueryPort,
    private val deviceTokenQueryPort: DeviceTokenQueryPort,
    private val notificationSenderPort: NotificationSenderPort,
) : SendNotificationUseCase {
    override fun send(notificationId: Long) {
        val notification = notificationQueryPort.getByNotificationIdWithActive(notificationId)
        val token = deviceTokenQueryPort.findByUserIdWithActive(notification.userId)?.token ?: run {
            log.debug { "[NOTIFICATION-SEND] no token: notificationId=$notificationId" }
            return
        }

        notificationSenderPort.sendNotification(
            notification = notification,
            tokens = listOf(token),
        )
        log.debug { "[NOTIFICATION-SEND] sent: notificationId=$notificationId" }
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
