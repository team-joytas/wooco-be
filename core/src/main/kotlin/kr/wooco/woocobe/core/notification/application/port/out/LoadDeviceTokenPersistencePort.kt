package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken

interface LoadDeviceTokenPersistencePort {
    fun getByDeviceToken(token: String): DeviceToken

    fun getByUserId(userId: Long): DeviceToken
}
