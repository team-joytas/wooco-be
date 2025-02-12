package kr.wooco.woocobe.mysql.plan.repository

import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlanPlaceJpaRepository : JpaRepository<PlanPlaceJpaEntity, Long> {
    fun findAllByPlanId(planId: Long): List<PlanPlaceJpaEntity>

    fun deleteAllByPlanId(planId: Long)

    fun findAllByPlanIdIn(planIds: List<Long>): List<PlanPlaceJpaEntity>
}
