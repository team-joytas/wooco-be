package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.notification.domain.exception.AlreadyDeletedNotificationException
import kr.wooco.woocobe.core.notification.domain.exception.InvalidNotificationOwnerException
import kr.wooco.woocobe.core.notification.domain.exception.NotExistsNotificationException
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
        when {
            this.userId != userId -> throw InvalidNotificationOwnerException
            this.status != NotificationStatus.ACTIVE -> throw NotExistsNotificationException
        }
        return copy(readStatus = NotificationReadStatus.READ)
    }

    fun delete(userId: Long): Notification {
        when {
            this.userId != userId -> throw InvalidNotificationOwnerException
            this.status != NotificationStatus.ACTIVE -> throw AlreadyDeletedNotificationException
        }
        return copy(status = NotificationStatus.DELETED)
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
