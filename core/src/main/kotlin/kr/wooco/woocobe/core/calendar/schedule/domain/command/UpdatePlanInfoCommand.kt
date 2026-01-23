package kr.wooco.woocobe.core.calendar.schedule.domain.command

import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan

data class UpdatePlanInfoCommand(
    val userId: Long,
    val planId: Long,
    val title: Plan.Title,
    val visitDate: Plan.VisitDate,
    val placeIds: List<Long>,
)
