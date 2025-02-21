package kr.wooco.woocobe.core.notification.application.port.`in`

import kr.wooco.woocobe.core.notification.application.port.`in`.results.DeviceTokenResult

fun interface ReadDeviceTokenUseCase {
    data class Query(
        val userId: Long,
    )

    fun readDeviceToken(query: Query): DeviceTokenResult
}
