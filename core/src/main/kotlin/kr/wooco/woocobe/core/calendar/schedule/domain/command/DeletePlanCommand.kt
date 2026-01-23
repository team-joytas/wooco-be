package kr.wooco.woocobe.core.calendar.schedule.domain.command

data class DeletePlanCommand(
    val userId: Long,
    val planId: Long,
)
