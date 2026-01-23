package kr.wooco.woocobe.core.calendar.schedule.application.port.out

import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import java.time.LocalDate

interface SchedulePlanQueryPort {

    fun getViewById(planId: Long): PlanView

    fun getViewAllByGroupIdInAndVisitDate(groupIds: List<Long>, visitDate: LocalDate): List<PlanView>

    fun getViewAllByGroupIdInAndVisitDateBetween(
        groupIds: List<Long>,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<PlanView>
}
