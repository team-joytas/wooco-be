package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.mysql.notification.entity.DeviceTokenJpaEntity
import org.springframework.stereotype.Component

@Component
internal class DeviceTokenPersistenceMapper {
    fun toDomain(deviceTokenJpaEntity: DeviceTokenJpaEntity): DeviceToken =
        DeviceToken(
            id = deviceTokenJpaEntity.id,
            userId = deviceTokenJpaEntity.userId,
            token = deviceTokenJpaEntity.token,
            isActive = deviceTokenJpaEntity.isActive,
        )

    fun toEntity(deviceToken: DeviceToken): DeviceTokenJpaEntity =
        DeviceTokenJpaEntity(
            id = deviceToken.id,
            userId = deviceToken.userId,
            token = deviceToken.token,
            isActive = deviceToken.isActive,
        )
}
