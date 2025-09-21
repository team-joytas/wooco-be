package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken.Token

interface DeviceTokenQueryPort {
    fun getByDeviceTokenId(deviceTokenId: Long): DeviceToken

    fun getByUserIdAndToken(
        userId: Long,
        token: Token,
    ): DeviceToken

    fun getAllByUserIdWithActive(userId: Long): List<DeviceToken>
}
