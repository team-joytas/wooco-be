package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface CreateNotificationUseCase {
    data class Command(
        val userId: Long,
        val targetId: Long,
        val targetName: String,
        val type: String,
    )

    fun createNotification(command: Command): Long
}
