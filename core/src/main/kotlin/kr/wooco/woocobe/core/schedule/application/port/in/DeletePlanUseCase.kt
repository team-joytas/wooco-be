package kr.wooco.woocobe.core.schedule.application.port.`in`

interface DeletePlanUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
    )

    fun deletePlan(command: DeletePlanUseCase.Command): Long
}
