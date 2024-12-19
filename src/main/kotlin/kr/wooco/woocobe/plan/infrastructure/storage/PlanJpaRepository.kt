package kr.wooco.woocobe.plan.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface PlanJpaRepository : JpaRepository<PlanEntity, Long> {
    fun findAllByUserId(userId: Long): List<PlanEntity>
}
