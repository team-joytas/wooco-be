package kr.wooco.woocobe.plan.adapter.out.persistence.repository

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanCategoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanCategoryJpaRepository : JpaRepository<PlanCategoryJpaEntity, Long> {
    fun findAllByPlanId(planId: Long): List<PlanCategoryJpaEntity>

    fun deleteAllByPlanId(planId: Long)

    fun findAllByPlanIdIn(planIds: List<Long>): List<PlanCategoryJpaEntity>
}
