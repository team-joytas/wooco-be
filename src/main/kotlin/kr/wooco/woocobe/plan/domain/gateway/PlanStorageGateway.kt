package kr.wooco.woocobe.plan.domain.gateway

import kr.wooco.woocobe.plan.domain.model.Plan

interface PlanStorageGateway {
    fun save(plan: Plan): Plan
}
