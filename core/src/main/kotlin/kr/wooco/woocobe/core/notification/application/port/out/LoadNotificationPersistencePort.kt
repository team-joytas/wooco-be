package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface LoadNotificationPersistencePort {
    fun getByNotificationId(id: Long): Notification

    fun getAllByUserId(userId: Long): List<Notification>
}
