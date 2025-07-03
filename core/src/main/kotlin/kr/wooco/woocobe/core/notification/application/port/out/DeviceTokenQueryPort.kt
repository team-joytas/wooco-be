package kr.wooco.woocobe.core.notification.application.port.out

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken.Token

interface DeviceTokenQueryPort {
    fun getByToken(token: Token): DeviceToken

    fun getAllByUserIdWithActive(userId: Long): List<DeviceToken>

    fun existsByToken(token: Token): Boolean
}
