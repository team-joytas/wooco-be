package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.command.MarkAsReadNotificationCommand

fun interface MarkAsReadNotificationUseCase {
    data class Command(
        val userId: Long,
        val notificationId: Long,
    ) {
        fun toMarkAsReadCommand(): MarkAsReadNotificationCommand =
            MarkAsReadNotificationCommand(
                userId = userId,
            )
    }

    fun markAsReadNotification(command: Command)
}
