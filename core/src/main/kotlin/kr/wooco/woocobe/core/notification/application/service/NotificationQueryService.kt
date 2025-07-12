package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.ReadAllNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.SendNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.results.DeviceTokenResult
import kr.wooco.woocobe.core.notification.application.port.`in`.results.NotificationResult
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationSenderPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationQueryService(
    private val notificationQueryPort: NotificationQueryPort,
    private val deviceTokenQueryPort: DeviceTokenQueryPort,
    private val notificationSenderPort: NotificationSenderPort,
) : ReadAllNotificationUseCase,
    SendNotificationUseCase,
    ReadDeviceTokenUseCase {
    @Transactional(readOnly = true)
    override fun readAllNotification(query: ReadAllNotificationUseCase.Query): List<NotificationResult> {
        val notifications = notificationQueryPort.getAllByUserIdWithActive(query.userId)
        return notifications.map { NotificationResult.from(it) }
    }

    override fun sendNotification(query: SendNotificationUseCase.Query) {
        val notification = notificationQueryPort.getByNotificationIdWithActive(query.notificationId)
        val tokens = deviceTokenQueryPort.getAllByUserIdWithActive(notification.userId).map { it.token }
        if (tokens.isNotEmpty()) {
            notificationSenderPort.sendNotification(notification = notification, tokens = tokens)
        }
    }

    override fun readDeviceToken(query: ReadDeviceTokenUseCase.Query): DeviceTokenResult {
        val deviceToken = deviceTokenQueryPort.getByUserIdAndToken(query.userId, DeviceToken.Token(query.token))
        return DeviceTokenResult.from(deviceToken)
    }
}
