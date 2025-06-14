package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.vo.DeviceTokenStatus
import kr.wooco.woocobe.core.notification.domain.vo.Token
import kr.wooco.woocobe.mysql.notification.entity.DeviceTokenJpaEntity

internal object DeviceTokenPersistenceMapper {
    fun toDomainEntity(deviceTokenJpaEntity: DeviceTokenJpaEntity): DeviceToken =
        DeviceToken(
            id = deviceTokenJpaEntity.id,
            userId = deviceTokenJpaEntity.userId,
            token = Token(deviceTokenJpaEntity.token),
            status = DeviceTokenStatus(deviceTokenJpaEntity.status),
        )

    fun toJpaEntity(deviceToken: DeviceToken): DeviceTokenJpaEntity =
        DeviceTokenJpaEntity(
            id = deviceToken.id,
            userId = deviceToken.userId,
            token = deviceToken.token.value,
            status = deviceToken.status.name,
        )
}
