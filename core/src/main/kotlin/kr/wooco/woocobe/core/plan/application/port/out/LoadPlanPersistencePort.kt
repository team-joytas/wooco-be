package kr.wooco.woocobe.core.plan.application.port.out

import kr.wooco.woocobe.core.plan.domain.entity.Plan

interface LoadPlanPersistencePort {
    fun getByPlanId(planId: Long): Plan

    fun getAllByUserId(userId: Long): List<Plan>

    fun getAllByIsSharedFalse(): List<Plan>
}
