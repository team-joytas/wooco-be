package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface CreateDeviceTokenUseCase {
    data class Command(
        val userId: Long,
        val token: String,
    )

    fun createDeviceToken(command: Command)
}
