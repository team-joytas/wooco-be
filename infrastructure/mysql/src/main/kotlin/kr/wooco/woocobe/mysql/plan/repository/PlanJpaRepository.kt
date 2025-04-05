package kr.wooco.woocobe.mysql.plan.repository

import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface PlanJpaRepository : JpaRepository<PlanJpaEntity, Long> {
    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.id = :planId
            AND p.status = 'ACTIVE'
        """,
    )
    fun findActiveById(planId: Long): PlanJpaEntity?

    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.userId = :userId
            AND p.status = 'ACTIVE'
        """,
    )
    fun findAllActiveByUserId(userId: Long): List<PlanJpaEntity>

    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.createdAt BETWEEN :startDate AND :endDate
            AND p.status = 'ACTIVE'
        """,
    )
    fun findAllActiveByCreatedAtBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<PlanJpaEntity>
}
