package kr.wooco.woocobe.core.notification.application.port.`in`.results

import kr.wooco.woocobe.core.notification.domain.entity.Notification
import java.time.LocalDateTime

data class NotificationResult(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val type: String,
    val createdAt: LocalDateTime,
    val readStatus: String,
) {
    companion object {
        fun from(notification: Notification): NotificationResult =
            NotificationResult(
                id = notification.id,
                targetId = notification.target.targetId,
                targetName = notification.target.targetName,
                userId = notification.userId,
                type = notification.target.type.name,
                createdAt = notification.createdAt,
                readStatus = notification.readStatus.name,
            )
    }
}
