package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface MarkAsReadNotificationUseCase {
    data class Command(
        val userId: Long,
        val notificationId: Long,
    )

    fun markAsReadNotification(command: Command)
}
