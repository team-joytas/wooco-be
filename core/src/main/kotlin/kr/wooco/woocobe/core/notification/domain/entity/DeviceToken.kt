package kr.wooco.woocobe.core.notification.domain.entity

import kr.wooco.woocobe.core.common.domain.entity.AggregateRoot
import kr.wooco.woocobe.core.notification.domain.command.DeleteDeviceTokenCommand
import kr.wooco.woocobe.core.notification.domain.command.RegisterDeviceTokenCommand
import kr.wooco.woocobe.core.notification.domain.command.UpdateDeviceTokenCommand
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

    fun delete(command: DeleteDeviceTokenCommand): DeviceToken {
        when {
            this.userId != command.userId -> throw InvalidDeviceTokenOwnerException
            this.status != DeviceTokenStatus.ACTIVE -> throw AlreadyDeletedDeviceTokenException
        }
        return copy(status = DeviceTokenStatus.DELETED)
    }

    fun update(command: UpdateDeviceTokenCommand): DeviceToken {
        when {
            this.userId != command.userId -> throw InvalidDeviceTokenOwnerException
            this.status != DeviceTokenStatus.ACTIVE -> throw AlreadyDeletedDeviceTokenException
        }
        return copy(
            token = command.token,
        )
    }

    companion object {
        fun create(
            command: RegisterDeviceTokenCommand,
            identifier: (DeviceToken) -> Long,
        ): DeviceToken =
            DeviceToken(
                id = 0L,
                userId = command.userId,
                token = command.token,
                status = DeviceTokenStatus.ACTIVE,
            ).let {
                it.copy(id = identifier.invoke(it))
            }
    }
}
