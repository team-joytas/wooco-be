package kr.wooco.woocobe.core.schedule.domain.command

import kr.wooco.woocobe.core.schedule.domain.entity.Plan

data class CreatePlanCommand(
    val userId: Long,
    val groupId: Long,
    val title: Plan.Title,
    val visitDate: Plan.VisitDate,
    val placeIds: List<Long>,
)
