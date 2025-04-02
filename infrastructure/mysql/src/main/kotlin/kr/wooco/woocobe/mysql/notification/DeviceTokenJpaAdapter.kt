package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.exception.NotExistsDeviceTokenException
import kr.wooco.woocobe.core.notification.domain.vo.Token
import kr.wooco.woocobe.mysql.notification.repository.DeviceTokenJpaRepository
import org.springframework.stereotype.Component

@Component
internal class DeviceTokenJpaAdapter(
    private val deviceTokenJpaRepository: DeviceTokenJpaRepository,
) : DeviceTokenQueryPort,
    DeviceTokenCommandPort {
    override fun getByToken(token: Token): DeviceToken {
        val deviceTokenJpaEntity = deviceTokenJpaRepository.findActiveByToken(token.value)
            ?: throw NotExistsDeviceTokenException
        return DeviceTokenJpaMapper.toDomainEntity(deviceTokenJpaEntity)
    }

    override fun getAllByUserId(userId: Long): List<DeviceToken> =
        deviceTokenJpaRepository.findAllActiveByUserId(userId).map { DeviceTokenJpaMapper.toDomainEntity(it) }

    override fun saveDeviceToken(deviceToken: DeviceToken): DeviceToken {
        val deviceTokenJpaEntity = DeviceTokenJpaMapper.toJpaEntity(deviceToken)
        deviceTokenJpaRepository.save(deviceTokenJpaEntity)
        return DeviceTokenJpaMapper.toDomainEntity(deviceTokenJpaEntity)
    }
}
