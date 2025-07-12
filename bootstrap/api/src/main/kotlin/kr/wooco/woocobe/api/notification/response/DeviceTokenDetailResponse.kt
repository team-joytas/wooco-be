package kr.wooco.woocobe.api.notification.response

import kr.wooco.woocobe.core.notification.application.port.`in`.results.DeviceTokenResult

// TODO: 프론트엔드 개발자와 협의 후 필요한 데이터 전달

data class DeviceTokenDetailResponse(
    val id: Long,
    val userId: Long,
    val token: String,
) {
    companion object {
        fun from(deviceTokenResult: DeviceTokenResult): DeviceTokenDetailResponse =
            DeviceTokenDetailResponse(
                id = deviceTokenResult.id,
                userId = deviceTokenResult.userId,
                token = deviceTokenResult.token,
            )

        fun of(
            id: Long,
            userId: Long,
            token: String,
        ): DeviceTokenDetailResponse =
            DeviceTokenDetailResponse(
                id = id,
                userId = userId,
                token = token,
            )
    }
}
