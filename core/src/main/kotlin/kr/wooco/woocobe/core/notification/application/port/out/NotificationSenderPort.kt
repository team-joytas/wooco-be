package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification
import kr.wooco.woocobe.core.notification.domain.vo.Token

interface NotificationSenderPort {
    fun sendNotification(
        notification: Notification,
        tokens: List<Token>,
    )
}
