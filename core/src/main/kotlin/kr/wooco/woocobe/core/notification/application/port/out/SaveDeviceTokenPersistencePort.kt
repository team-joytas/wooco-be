package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

interface SaveDeviceTokenPersistencePort {
    fun saveDeviceToken(deviceToken: DeviceToken): DeviceToken
}
