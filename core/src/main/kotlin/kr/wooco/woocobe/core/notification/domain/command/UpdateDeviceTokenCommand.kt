package kr.wooco.woocobe.core.notification.domain.command

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

data class UpdateDeviceTokenCommand(
    val userId: Long,
    val tokenId: Long,
    val token: DeviceToken.Token,
)
