package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.notification.domain.command.CreateNotificationCommand
import kr.wooco.woocobe.core.notification.domain.command.DeleteNotificationCommand
import kr.wooco.woocobe.core.notification.domain.command.MarkAsReadNotificationCommand
import kr.wooco.woocobe.core.notification.domain.exception.AlreadyDeletedNotificationException
import kr.wooco.woocobe.core.notification.domain.exception.InvalidNotificationOwnerException
import kr.wooco.woocobe.core.notification.domain.exception.NotExistsNotificationException
import kr.wooco.woocobe.core.notification.domain.vo.NotificationReadStatus
import kr.wooco.woocobe.core.notification.domain.vo.NotificationStatus
import kr.wooco.woocobe.core.notification.domain.vo.NotificationTarget
import java.time.LocalDateTime

// TODO: View Model 분리

data class Notification(
    override val id: Long,
    val userId: Long,
    val target: NotificationTarget,
    val createdAt: LocalDateTime,
    val status: NotificationStatus,
    val readStatus: NotificationReadStatus,
) : AggregateRoot() {
    fun markAsRead(command: MarkAsReadNotificationCommand): Notification {
        when {
            this.userId != command.userId -> throw InvalidNotificationOwnerException
            this.status != NotificationStatus.ACTIVE -> throw NotExistsNotificationException
        }
        return copy(readStatus = NotificationReadStatus.READ)
    }

    fun delete(command: DeleteNotificationCommand): Notification {
        when {
            this.userId != command.userId -> throw InvalidNotificationOwnerException
            this.status != NotificationStatus.ACTIVE -> throw AlreadyDeletedNotificationException
        }
        return copy(status = NotificationStatus.DELETED)
    }

    companion object {
        /**
         * 새로운 알림을 생성합니다.
         *
         * @param command Notification 엔티티 생성을 위한 Command 객체
         * @param identifier 생성된 Notification 엔티티의 새로운 ID를 할당하는 메서드
         * @author Junseoparkk
         */
        fun create(
            command: CreateNotificationCommand,
            identifier: (Notification) -> Long,
        ): Notification =
            Notification(
                id = 0L,
                userId = command.userId,
                target = command.target,
                createdAt = LocalDateTime.now(),
                status = NotificationStatus.ACTIVE,
                readStatus = NotificationReadStatus.UNREAD,
            ).let {
                it.copy(id = identifier.invoke(it))
            }
    }
}
