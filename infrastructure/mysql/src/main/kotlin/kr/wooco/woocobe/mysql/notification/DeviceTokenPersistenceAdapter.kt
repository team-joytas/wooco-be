package kr.wooco.woocobe.mysql.notification

import kr.wooco.woocobe.core.notification.application.port.out.DeleteDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.LoadDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.application.port.out.SaveDeviceTokenPersistencePort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.mysql.notification.repository.DeviceTokenJpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
internal class DeviceTokenPersistenceAdapter(
    private val deviceTokenJpaRepository: DeviceTokenJpaRepository,
    private val deviceTokenPersistenceMapper: DeviceTokenPersistenceMapper,
) : LoadDeviceTokenPersistencePort,
    SaveDeviceTokenPersistencePort,
    DeleteDeviceTokenPersistencePort {
    @Transactional(readOnly = true)
    override fun getByDeviceToken(token: String): DeviceToken {
        val deviceTokenJpaEntity = deviceTokenJpaRepository.findByToken(token)
        return deviceTokenPersistenceMapper.toDomain(deviceTokenJpaEntity)
    }

    @Transactional(readOnly = true)
    override fun getByUserId(userId: Long): DeviceToken {
        val deviceTokenJpaEntity = deviceTokenJpaRepository.findByUserId(userId)
        return deviceTokenPersistenceMapper.toDomain(deviceTokenJpaEntity)
    }

    @Transactional(readOnly = true)
    override fun saveDeviceToken(deviceToken: DeviceToken): DeviceToken {
        val deviceTokenJpaEntity = deviceTokenPersistenceMapper.toEntity(deviceToken)
        deviceTokenJpaRepository.save(deviceTokenJpaEntity)
        return deviceTokenPersistenceMapper.toDomain(deviceTokenJpaEntity)
    }

    @Transactional(readOnly = true)
    override fun deleteDeviceToKen(token: String) {
        deviceTokenJpaRepository.deleteByToken(token)
    }
}
