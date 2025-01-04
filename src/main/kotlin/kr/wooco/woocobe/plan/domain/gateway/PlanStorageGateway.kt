package kr.wooco.woocobe.plan.domain.gateway

import kr.wooco.woocobe.plan.domain.model.Plan

interface PlanStorageGateway {
    fun save(plan: Plan): Plan

    fun getByPlanId(planId: Long): Plan

    fun getAllByUserId(userId: Long): List<Plan>

    fun deleteByPlanId(planId: Long)
}
