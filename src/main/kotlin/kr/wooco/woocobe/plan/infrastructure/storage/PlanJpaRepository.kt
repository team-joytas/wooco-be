package kr.wooco.woocobe.plan.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlanJpaRepository : JpaRepository<PlanEntity, Long> {
    @Query("SELECT p FROM PlanEntity p WHERE p.userId = :userId")
    fun findAllByUserId(userId: Long): List<PlanEntity>
}
