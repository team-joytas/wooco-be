package kr.wooco.woocobe.core.schedule.domain.command

data class DeletePlanCommand(
    val userId: Long,
    val planId: Long,
)
