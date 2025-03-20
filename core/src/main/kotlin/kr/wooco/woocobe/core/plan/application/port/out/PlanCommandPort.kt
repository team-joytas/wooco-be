package kr.wooco.woocobe.core.plan.application.port.out

import kr.wooco.woocobe.core.plan.domain.entity.Plan

interface PlanCommandPort {
    fun savePlan(plan: Plan): Plan

    fun deleteByPlanId(planId: Long)
}
