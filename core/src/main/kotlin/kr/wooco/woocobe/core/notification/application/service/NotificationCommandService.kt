package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.DeleteDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.MarkAsReadNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.RegisterDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.UpdateDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

// TODO: 디바이스 토큰 조회 조건 변경 (id 기반 조회)

@Service
class NotificationCommandService(
    private val notificationQueryPort: NotificationQueryPort,
    private val notificationCommandPort: NotificationCommandPort,
    private val deviceTokenQueryPort: DeviceTokenQueryPort,
    private val deviceTokenCommandPort: DeviceTokenCommandPort,
) : CreateNotificationUseCase,
    MarkAsReadNotificationUseCase,
    RegisterDeviceTokenUseCase,
    DeleteDeviceTokenUseCase,
    UpdateDeviceTokenUseCase {
    @Transactional
    override fun createNotification(command: CreateNotificationUseCase.Command): Long {
        val notification = Notification.create(command.toCreateCommand()) { notification ->
            notificationCommandPort.saveNotification(notification)
        }
        return notification.id
    }

    @Transactional
    override fun markAsReadNotification(command: MarkAsReadNotificationUseCase.Command) {
        val notification = notificationQueryPort.getByNotificationId(command.notificationId)
        val readNotification = notification.markAsRead(command.toMarkAsReadCommand())
        notificationCommandPort.saveNotification(readNotification)
    }

    @Transactional
    override fun registerDeviceToken(command: RegisterDeviceTokenUseCase.Command): Long {
        val deviceToken = DeviceToken.create(command.toRegisterDeviceTokenCommand()) { deviceToken ->
            deviceTokenCommandPort.saveDeviceToken(deviceToken)
        }
        return deviceToken.id
    }

    @Transactional
    override fun deleteDeviceToken(command: DeleteDeviceTokenUseCase.Command) {
        val deviceToken = deviceTokenQueryPort.getByDeviceTokenId(command.tokenId)
        val deletedDeviceToken = deviceToken.delete(command.toDeleteCommand())
        deviceTokenCommandPort.saveDeviceToken(deletedDeviceToken)
    }

    @Transactional
    override fun updateDeviceToken(command: UpdateDeviceTokenUseCase.Command): Long {
        val deviceToken = deviceTokenQueryPort.getByDeviceTokenId(command.tokenId)
        val updatedDeviceToken = deviceToken.update(command.toUpdateDeviceTokenCommand())
        deviceTokenCommandPort.saveDeviceToken(updatedDeviceToken)
        return deviceToken.id
    }
}
