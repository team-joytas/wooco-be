package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface UpdateNotificationUseCase {
    data class Command(
        val notificationId: Long,
    )

    fun updateNotification(command: Command)
}
