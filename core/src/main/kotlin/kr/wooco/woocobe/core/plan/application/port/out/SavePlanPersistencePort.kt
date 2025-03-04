package kr.wooco.woocobe.core.plan.application.port.out

import kr.wooco.woocobe.core.plan.domain.entity.Plan

interface SavePlanPersistencePort {
    fun savePlan(plan: Plan): Plan
}
