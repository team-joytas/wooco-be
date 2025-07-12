package kr.wooco.woocobe.core.notification.domain.command

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

data class RegisterDeviceTokenCommand(
    val userId: Long,
    val token: DeviceToken.Token,
)
