package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.command.DeleteDeviceTokenCommand

fun interface DeleteDeviceTokenUseCase {
    data class Command(
        val userId: Long,
        val token: String,
    ) {
        fun toDeleteCommand(): DeleteDeviceTokenCommand =
            DeleteDeviceTokenCommand(
                userId = userId,
            )
    }

    fun deleteDeviceToken(command: Command)
}
