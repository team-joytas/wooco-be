package kr.wooco.woocobe.core.plan.application.port.out

import kr.wooco.woocobe.core.plan.domain.entity.Plan
import java.time.LocalDateTime

interface PlanQueryPort {
    fun getByPlanId(planId: Long): Plan

    fun getActiveByPlanId(planId: Long): Plan

    fun getAllActiveByUserId(userId: Long): List<Plan>

    fun getAllActiveByCreatedAtBetween(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Plan>
}
