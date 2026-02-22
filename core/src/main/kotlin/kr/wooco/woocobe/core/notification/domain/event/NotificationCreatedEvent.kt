package kr.wooco.woocobe.core.notification.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.notification.domain.entity.Notification

data class NotificationCreatedEvent(
    override val aggregateId: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val type: String,
) : DomainEvent() {
    companion object {
        fun from(notification: Notification): NotificationCreatedEvent =
            NotificationCreatedEvent(
                aggregateId = notification.id,
                userId = notification.userId,
                targetId = notification.target.targetId,
                targetName = notification.target.targetName,
                type = notification.target.type.name,
            )
    }
}
