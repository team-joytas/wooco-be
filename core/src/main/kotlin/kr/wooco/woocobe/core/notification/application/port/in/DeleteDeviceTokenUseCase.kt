package kr.wooco.woocobe.core.notification.application.port.`in`

fun interface DeleteDeviceTokenUseCase {
    data class Command(
        val token: String,
    )

    fun deleteDeviceToken(command: Command)
}
