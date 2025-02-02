package kr.wooco.woocobe.plan.application.port.out

import kr.wooco.woocobe.plan.domain.entity.Plan

interface SavePlanPersistencePort {
    fun savePlan(plan: Plan): Plan
}
