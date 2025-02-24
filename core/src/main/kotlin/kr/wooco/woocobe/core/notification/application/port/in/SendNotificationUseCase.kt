package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.entity.Notification

interface SendNotificationUseCase {
    data class Request(
        val notification: Notification,
        val token: String,
    )

    fun sendNotification(request: Request)
}
