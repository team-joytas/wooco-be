package kr.wooco.woocobe.core.plan.application.port.`in`

fun interface DeletePlanUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
    )

    fun deletePlan(command: Command)
}
