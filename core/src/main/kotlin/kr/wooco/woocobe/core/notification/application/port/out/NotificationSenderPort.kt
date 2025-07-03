package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken.Token
import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface NotificationSenderPort {
    fun sendNotification(
        notification: Notification,
        tokens: List<Token>,
    )
}
