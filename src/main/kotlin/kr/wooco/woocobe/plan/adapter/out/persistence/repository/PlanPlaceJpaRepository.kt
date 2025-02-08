package kr.wooco.woocobe.plan.adapter.out.persistence.repository

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanPlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanPlaceJpaRepository : JpaRepository<PlanPlaceJpaEntity, Long> {
    fun findAllByPlanId(planId: Long): List<PlanPlaceJpaEntity>

    fun deleteAllByPlanId(planId: Long)

    fun findAllByPlanIdIn(planIds: List<Long>): List<PlanPlaceJpaEntity>
}
