package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface NotificationCommandPort {
    fun saveNotification(notification: Notification): Long
}
