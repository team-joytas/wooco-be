package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.command.CreateNotificationCommand
import kr.wooco.woocobe.core.notification.domain.vo.NotificationTarget
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType

fun interface CreateNotificationUseCase {
    data class Command(
        val userId: Long,
        val targetId: Long,
        val targetName: String,
        val type: String,
    ) {
        fun toCreateCommand(): CreateNotificationCommand =
            CreateNotificationCommand(
                userId = userId,
                target = NotificationTarget(
                    targetId = targetId,
                    targetName = targetName,
                    type = NotificationType.invoke(type),
                ),
            )
    }

    fun createNotification(command: Command): Long
}
