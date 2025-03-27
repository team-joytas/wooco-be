package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.DeviceTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DeviceTokenJpaRepository : JpaRepository<DeviceTokenJpaEntity, Long> {
    @Query(
        """
            SELECT d FROM DeviceTokenJpaEntity d
            WHERE d.token = :token
            AND d.status = 'ACTIVE'
        """,
    )
    fun findActiveByToken(token: String): DeviceTokenJpaEntity?

    @Query(
        """
            SELECT d FROM DeviceTokenJpaEntity d
            WHERE d.userId = :userId
            AND d.status = 'ACTIVE'
        """,
    )
    fun findAllActiveByUserId(
        @Param("userId") userId: Long,
    ): List<DeviceTokenJpaEntity>
}
