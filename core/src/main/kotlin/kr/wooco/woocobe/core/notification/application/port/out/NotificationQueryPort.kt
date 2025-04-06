package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface NotificationQueryPort {
    fun getByNotificationId(id: Long): Notification

    fun getActiveByNotificationId(id: Long): Notification

    fun getAllActiveByUserId(userId: Long): List<Notification>
}
