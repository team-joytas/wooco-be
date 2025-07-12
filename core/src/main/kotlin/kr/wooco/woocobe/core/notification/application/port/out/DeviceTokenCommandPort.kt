package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

interface DeviceTokenCommandPort {
    fun saveDeviceToken(deviceToken: DeviceToken): Long
}
