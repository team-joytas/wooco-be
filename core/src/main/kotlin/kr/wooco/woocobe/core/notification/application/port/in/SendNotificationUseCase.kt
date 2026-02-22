package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface SendNotificationUseCase {
    fun send(notificationId: Long)
}
