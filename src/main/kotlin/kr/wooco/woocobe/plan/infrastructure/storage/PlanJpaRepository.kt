package kr.wooco.woocobe.plan.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlanJpaRepository : JpaRepository<PlanEntity, Long> {
    @Query("SELECT p FROM PlanEntity p LEFT JOIN FETCH p.user WHERE p.id = :id")
    fun findByIdWithUser(id: Long): PlanEntity?

    @Query("SELECT p FROM PlanEntity p LEFT JOIN FETCH p.user WHERE p.user.id = :userId")
    fun findAllByUserId(userId: Long): List<PlanEntity>?
}
