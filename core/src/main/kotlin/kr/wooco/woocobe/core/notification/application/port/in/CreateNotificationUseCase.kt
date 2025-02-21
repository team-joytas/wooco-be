package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.entity.Notification

fun interface CreateNotificationUseCase {
    data class Command(
        val userId: Long,
        val targetId: Long,
        val targetName: String,
        val type: String,
    )

    fun createNotification(command: Command): Notification
}
