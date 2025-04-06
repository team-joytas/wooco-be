package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface NotificationQueryPort {
    fun getByNotificationId(notificationId: Long): Notification

    fun getByNotificationIdWithActive(notificationId: Long): Notification

    fun getAllByUserIdWithActive(userId: Long): List<Notification>
}
