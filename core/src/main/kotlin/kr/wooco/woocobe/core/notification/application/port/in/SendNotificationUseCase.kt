package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface SendNotificationUseCase {
    data class Query(
        val notificationId: Long,
    )

    fun sendNotification(query: Query)
}
