package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.notification.domain.exception.InvalidNotificationOwnerException
import kr.wooco.woocobe.core.notification.domain.vo.NotificationReadStatus
import kr.wooco.woocobe.core.notification.domain.vo.NotificationStatus
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val userId: Long,
    val targetId: Long,
    val targetName: String,
    val type: NotificationType,
    val createdAt: LocalDateTime,
    val status: NotificationStatus,
    val readStatus: NotificationReadStatus,
) {
    fun markAsRead(userId: Long): Notification {
        validateOwner(userId)
        return copy(readStatus = NotificationReadStatus.READ)
    }

    fun softDelete(userId: Long): Notification {
        validateOwner(userId)
        return copy(status = NotificationStatus.DELETED)
    }

    private fun validateOwner(userId: Long) {
        if (this.userId != userId) throw InvalidNotificationOwnerException
    }

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
                type = type,
                createdAt = LocalDateTime.now(),
                status = NotificationStatus.ACTIVE,
                readStatus = NotificationReadStatus.UNREAD,
            )
    }
}
