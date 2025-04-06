package kr.wooco.woocobe.core.plan.application.port.out

import kr.wooco.woocobe.core.plan.domain.entity.Plan
import java.time.LocalDateTime

interface PlanQueryPort {
    fun getByPlanId(planId: Long): Plan

    fun getByPlanIdWithActive(planId: Long): Plan

    fun getAllByUserIdWithActive(userId: Long): List<Plan>

    fun getAllByCreatedAtBetweenWithActive(
        startDate: LocalDateTime,
        endDate: LocalDateTime,
    ): List<Plan>
}
