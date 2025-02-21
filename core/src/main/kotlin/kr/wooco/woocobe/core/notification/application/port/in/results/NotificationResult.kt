package kr.wooco.woocobe.core.notification.application.port.`in`.results

import kr.wooco.woocobe.core.notification.domain.entity.Notification
import java.time.LocalDateTime

data class NotificationResult(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val isRead: Boolean,
    val type: String,
    val sentAt: LocalDateTime,
) {
    companion object {
        fun from(notification: Notification): NotificationResult =
            NotificationResult(
                id = notification.id,
                targetId = notification.targetId,
                targetName = notification.targetName,
                userId = notification.userId,
                isRead = notification.isRead,
                type = notification.type.name,
                sentAt = notification.sentAt,
            )
    }
}
