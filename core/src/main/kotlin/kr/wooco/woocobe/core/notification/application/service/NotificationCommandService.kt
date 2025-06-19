package kr.wooco.woocobe.core.notification.application.service

import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.DeleteDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.MarkAsReadNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.RegisterDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.NotificationQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import kr.wooco.woocobe.core.notification.domain.vo.Target
import kr.wooco.woocobe.core.notification.domain.vo.Token
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationCommandService(
    private val notificationQueryPort: NotificationQueryPort,
    private val notificationCommandPort: NotificationCommandPort,
    private val deviceTokenQueryPort: DeviceTokenQueryPort,
    private val deviceTokenCommandPort: DeviceTokenCommandPort,
) : CreateNotificationUseCase,
    MarkAsReadNotificationUseCase,
    RegisterDeviceTokenUseCase,
    DeleteDeviceTokenUseCase {
    @Transactional
    override fun createNotification(command: CreateNotificationUseCase.Command): Long {
        val target = Target(targetId = command.targetId, targetName = command.targetName)
        val notification = Notification.create(
            userId = command.userId,
            target = target,
            type = NotificationType(command.type),
        )
        return notificationCommandPort.saveNotification(notification).id
    }

    @Transactional
    override fun markAsReadNotification(command: MarkAsReadNotificationUseCase.Command) {
        val notification = notificationQueryPort.getByNotificationId(command.notificationId)
        val readNotification = notification.markAsRead(command.userId)
        notificationCommandPort.saveNotification(readNotification)
    }

    @Transactional
    override fun registerDeviceToken(command: RegisterDeviceTokenUseCase.Command) {
        val token = Token(command.token)

        if (deviceTokenQueryPort.existsByToken(token)) return

        val deviceToken = DeviceToken.create(
            userId = command.userId,
            token = token,
        )
        deviceTokenCommandPort.saveDeviceToken(deviceToken)
    }

    @Transactional
    override fun deleteDeviceToken(command: DeleteDeviceTokenUseCase.Command) {
        val token = Token(command.token)
        val deviceToken = deviceTokenQueryPort.getByToken(token)
        val deletedDeviceToken = deviceToken.delete(command.userId)
        deviceTokenCommandPort.saveDeviceToken(deletedDeviceToken)
    }
}
