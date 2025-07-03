package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.notification.domain.exception.AlreadyDeletedDeviceTokenException
import kr.wooco.woocobe.core.notification.domain.exception.InvalidDeviceTokenOwnerException
import kr.wooco.woocobe.core.notification.domain.vo.DeviceTokenStatus

data class DeviceToken(
    override val id: Long,
    val userId: Long,
    val token: Token,
    val status: DeviceTokenStatus,
) : AggregateRoot() {
    @JvmInline
    value class Token(
        val value: String,
    ) {
        init {
            require(value.isNotBlank()) { "토큰은 빈 값일 수 없습니다." }
        }
    }

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
