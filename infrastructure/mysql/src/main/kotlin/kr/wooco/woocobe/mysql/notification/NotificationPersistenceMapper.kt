package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.NotificationReadStatus
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import kr.wooco.woocobe.mysql.notification.entity.NotificationJpaEntity

internal object NotificationPersistenceMapper {
    fun toDomainEntity(notificationJpaEntity: NotificationJpaEntity): Notification =
        Notification(
            id = notificationJpaEntity.id,
            userId = notificationJpaEntity.userId,
            targetId = notificationJpaEntity.targetId,
            targetName = notificationJpaEntity.targetName,
            type = NotificationType.invoke(notificationJpaEntity.type),
            createdAt = notificationJpaEntity.createdAt,
            readStatus = NotificationReadStatus.invoke(notificationJpaEntity.readStatus),
        )

    fun toJpaEntity(notification: Notification): NotificationJpaEntity =
        NotificationJpaEntity(
            id = notification.id,
            userId = notification.userId,
            targetId = notification.targetId,
            targetName = notification.targetName,
            type = notification.type.name,
            readStatus = notification.readStatus.name,
        )
}
