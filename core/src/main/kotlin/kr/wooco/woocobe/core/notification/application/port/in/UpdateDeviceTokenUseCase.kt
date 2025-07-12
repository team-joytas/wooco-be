package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.domain.command.UpdateDeviceTokenCommand
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

interface UpdateDeviceTokenUseCase {
    data class Command(
        val userId: Long,
        val tokenId: Long,
        val token: String,
    ) {
        fun toUpdateDeviceTokenCommand(): UpdateDeviceTokenCommand =
            UpdateDeviceTokenCommand(
                userId = userId,
                tokenId = tokenId,
                token = DeviceToken.Token(token),
            )
    }

    fun updateDeviceToken(command: Command): Long
}
