package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    var isRead: Boolean,
    val type: NotificationType,
    val sentAt: LocalDateTime,
) {
    fun read() = apply { if (!isRead) isRead = true }

    companion object {
        fun create(
            userId: Long,
            targetId: Long,
            targetName: String,
            type: NotificationType,
        ): Notification =
            Notification(
                id = 0L,
                userId = userId,
                targetId = targetId,
                targetName = targetName,
                isRead = false,
                type = type,
                sentAt = LocalDateTime.now(),
            )
    }
}
