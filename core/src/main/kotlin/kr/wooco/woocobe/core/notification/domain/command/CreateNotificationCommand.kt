package kr.wooco.woocobe.core.notification.domain.command

import kr.wooco.woocobe.core.notification.domain.vo.NotificationTarget

data class CreateNotificationCommand(
    val userId: Long,
    val target: NotificationTarget,
)
