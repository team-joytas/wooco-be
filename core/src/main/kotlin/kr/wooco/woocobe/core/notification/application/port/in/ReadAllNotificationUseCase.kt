package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.application.port.`in`.results.NotificationResult

fun interface ReadAllNotificationUseCase {
    data class Query(
        val userId: Long,
    )

    fun readAllNotification(query: Query): List<NotificationResult>
}
