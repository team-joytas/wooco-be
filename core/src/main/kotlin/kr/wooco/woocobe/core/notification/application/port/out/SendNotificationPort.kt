package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface SendNotificationPort {
    fun sendNotification(
        notification: Notification,
        token: String,
    )
}
