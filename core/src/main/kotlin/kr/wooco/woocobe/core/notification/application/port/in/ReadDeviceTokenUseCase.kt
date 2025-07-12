package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.application.port.`in`.results.DeviceTokenResult

interface ReadDeviceTokenUseCase {
    data class Query(
        val userId: Long,
        val token: String,
    )

    fun readDeviceToken(query: Query): DeviceTokenResult
}
