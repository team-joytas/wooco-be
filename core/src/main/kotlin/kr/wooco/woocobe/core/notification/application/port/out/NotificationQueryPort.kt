package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface NotificationQueryPort {
    fun getByNotificationId(notificationId: Long): Notification

    fun getActiveByNotificationId(notificationId: Long): Notification

    fun getAllActiveByUserId(userId: Long): List<Notification>
}
