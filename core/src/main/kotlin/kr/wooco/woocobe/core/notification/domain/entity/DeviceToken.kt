package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.notification.domain.exception.AlreadyDeletedDeviceTokenException
import kr.wooco.woocobe.core.notification.domain.exception.InvalidDeviceTokenOwnerException
import kr.wooco.woocobe.core.notification.domain.vo.DeviceTokenStatus
import kr.wooco.woocobe.core.notification.domain.vo.Token

data class DeviceToken(
    override val id: Long,
    val userId: Long,
    val token: Token,
    val status: DeviceTokenStatus,
) : AggregateRoot() {
    fun delete(userId: Long): DeviceToken {
        when {
            this.userId != userId -> throw InvalidDeviceTokenOwnerException
            this.status != DeviceTokenStatus.ACTIVE -> throw AlreadyDeletedDeviceTokenException
        }
        return copy(status = DeviceTokenStatus.DELETED)
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
                status = DeviceTokenStatus.ACTIVE,
            )
    }
}
