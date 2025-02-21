package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface SaveNotificationPersistencePort {
    fun saveNotification(notification: Notification): Notification
}
