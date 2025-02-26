package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.notification.domain.exception.InvalidDeviceTokenOwnerException
import kr.wooco.woocobe.core.notification.domain.vo.Token

data class DeviceToken(
    val id: Long,
    val userId: Long,
    val token: Token,
) {
    fun validateOwner(userId: Long) {
        if (this.userId != userId) throw InvalidDeviceTokenOwnerException
    }

    companion object {
        fun create(
            userId: Long,
            token: Token,
        ): DeviceToken =
            DeviceToken(
                id = 0L,
                userId = userId,
                token = token,
            )
    }
}
