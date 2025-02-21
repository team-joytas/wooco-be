package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import kr.wooco.woocobe.mysql.notification.entity.NotificationJpaEntity
import org.springframework.stereotype.Component

@Component
internal class NotificationPersistenceMapper {
    fun toDomain(notificationJpaEntity: NotificationJpaEntity): Notification =
        Notification(
            id = notificationJpaEntity.id,
            userId = notificationJpaEntity.userId,
            targetId = notificationJpaEntity.targetId,
            targetName = notificationJpaEntity.targetName,
            isRead = notificationJpaEntity.isRead,
            type = NotificationType.invoke(notificationJpaEntity.type),
            sentAt = notificationJpaEntity.createdAt,
        )

    fun toEntity(notification: Notification): NotificationJpaEntity =
        NotificationJpaEntity(
            id = notification.id,
            userId = notification.userId,
            targetId = notification.targetId,
            targetName = notification.targetName,
            isRead = notification.isRead,
            type = notification.type.name,
        )
}
