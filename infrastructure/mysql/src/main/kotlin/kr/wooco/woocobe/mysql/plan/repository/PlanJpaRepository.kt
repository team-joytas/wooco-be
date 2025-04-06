package kr.wooco.woocobe.mysql.plan.repository

import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface PlanJpaRepository : JpaRepository<PlanJpaEntity, Long> {
    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.id = :id
            AND p.status = :status
        """,
    )
    fun findByIdAndStatus(
        id: Long,
        status: String,
    ): PlanJpaEntity?

    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.userId = :userId
            AND p.status = :status
        """,
    )
    fun findAllByUserIdAndStatus(
        userId: Long,
        status: String,
    ): List<PlanJpaEntity>

    @Query(
        """
            SELECT p FROM PlanJpaEntity p
            WHERE p.createdAt BETWEEN :startDate AND :endDate
            AND p.status = :status
        """,
    )
    fun findAllByCreatedAtBetweenAndStatus(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
        status: String,
    ): List<PlanJpaEntity>
}
