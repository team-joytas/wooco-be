package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.vo.Token

interface DeviceTokenCommandPort {
    fun saveDeviceToken(deviceToken: DeviceToken): DeviceToken

    fun deleteByToKen(token: Token)
}
