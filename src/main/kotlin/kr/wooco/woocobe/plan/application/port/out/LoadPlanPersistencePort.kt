package kr.wooco.woocobe.plan.application.port.out

import kr.wooco.woocobe.plan.domain.entity.Plan

interface LoadPlanPersistencePort {
    fun getByPlanId(planId: Long): Plan

    fun getAllByUserId(userId: Long): List<Plan>
}
