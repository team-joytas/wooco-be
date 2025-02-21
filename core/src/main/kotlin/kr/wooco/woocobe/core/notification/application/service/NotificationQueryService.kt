package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.ReadAllNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.results.DeviceTokenResult
import kr.wooco.woocobe.core.notification.application.port.`in`.results.NotificationResult
import kr.wooco.woocobe.core.notification.application.port.out.LoadDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.LoadNotificationPersistencePort
import org.springframework.stereotype.Service

@Service
class NotificationQueryService(
    private val loadDeviceTokenPersistencePort: LoadDeviceTokenPersistencePort,
    private val loadNotificationPersistencePort: LoadNotificationPersistencePort,
) : ReadAllNotificationUseCase,
    ReadDeviceTokenUseCase {
    override fun readAllNotification(query: ReadAllNotificationUseCase.Query): List<NotificationResult> {
        val notifications = loadNotificationPersistencePort.getAllByUserId(query.userId)
        return notifications.map { NotificationResult.from(it) }
    }

    override fun readDeviceToken(query: ReadDeviceTokenUseCase.Query): DeviceTokenResult {
        val deviceToken = loadDeviceTokenPersistencePort.getByUserId(query.userId)
        return DeviceTokenResult.from(deviceToken)
    }
}
