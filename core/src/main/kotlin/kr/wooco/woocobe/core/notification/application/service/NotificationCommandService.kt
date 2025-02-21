package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.CreateDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.DeleteDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.UpdateNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.out.DeleteDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.LoadNotificationPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.SaveDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.SaveNotificationPersistencePort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationCommandService(
    private val loadNotificationPersistencePort: LoadNotificationPersistencePort,
    private val saveNotificationPersistencePort: SaveNotificationPersistencePort,
    private val saveDeviceTokenPersistencePort: SaveDeviceTokenPersistencePort,
    private val deleteDeviceTokenPersistencePort: DeleteDeviceTokenPersistencePort,
) : CreateNotificationUseCase,
    UpdateNotificationUseCase,
    CreateDeviceTokenUseCase,
    DeleteDeviceTokenUseCase {
    @Transactional
    override fun createNotification(command: CreateNotificationUseCase.Command): Notification {
        val notification = Notification.create(
            userId = command.userId,
            targetId = command.targetId,
            targetName = command.targetName,
            type = NotificationType.invoke(command.type),
        )
        val savedNotification = saveNotificationPersistencePort.saveNotification(notification)
        return savedNotification
    }

    @Transactional
    override fun updateNotification(command: UpdateNotificationUseCase.Command) {
        val notification = loadNotificationPersistencePort.getByNotificationId(command.notificationId)
        notification.read()
        saveNotificationPersistencePort.saveNotification(notification)
    }

    @Transactional
    override fun createDeviceToken(command: CreateDeviceTokenUseCase.Command) {
        val deviceToken = DeviceToken.create(
            userId = command.userId,
            token = command.token,
        )
        saveDeviceTokenPersistencePort.saveDeviceToken(deviceToken)
    }

    @Transactional
    override fun deleteDeviceToken(command: DeleteDeviceTokenUseCase.Command) {
        deleteDeviceTokenPersistencePort.deleteDeviceToKen(command.token)
    }
}
