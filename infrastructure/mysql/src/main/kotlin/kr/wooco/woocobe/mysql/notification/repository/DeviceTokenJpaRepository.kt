package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.DeviceTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DeviceTokenJpaRepository : JpaRepository<DeviceTokenJpaEntity, Long> {
    fun findByToken(token: String): DeviceTokenJpaEntity?

    fun findAllByUserId(userId: Long): List<DeviceTokenJpaEntity>

    fun deleteByToken(token: String)
}
