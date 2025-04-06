package kr.wooco.woocobe.mysql.notification.repository

import kr.wooco.woocobe.mysql.notification.entity.DeviceTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DeviceTokenJpaRepository : JpaRepository<DeviceTokenJpaEntity, Long> {
    fun findByToken(token: String): DeviceTokenJpaEntity?

    @Query(
        """
            SELECT d FROM DeviceTokenJpaEntity d
            WHERE d.userId = :userId
            AND d.status = :status
        """,
    )
    fun findAllByUserIdAndStatus(
        userId: Long,
        status: String,
    ): List<DeviceTokenJpaEntity>
}
