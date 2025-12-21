package kr.wooco.woocobe.core.schedule.application.port.out

import kr.wooco.woocobe.core.schedule.application.port.out.dto.PlanView
import java.time.LocalDate

interface SchedulePlanQueryPort {

    fun getViewByIdWithActive(planId: Long): PlanView

    fun getViewAllByGroupIdInAndVisitDateWithActive(groupIds: List<Long>, visitDate: LocalDate): List<PlanView>

    fun getViewAllByGroupIdInAndVisitDateBetweenWithActive(
        groupIds: List<Long>,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<PlanView>
}
