package kr.wooco.woocobe.core.notification.application.port.`in`.results

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

data class DeviceTokenResult(
    val userId: Long,
    val token: String,
) {
    companion object {
        fun from(deviceToken: DeviceToken): DeviceTokenResult =
            DeviceTokenResult(
                userId = deviceToken.userId,
                token = deviceToken.token,
            )
    }
}
