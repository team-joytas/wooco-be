package kr.wooco.woocobe.core.plan.application.port.`in`

fun interface SharePlanUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
    )

    fun sharePlan(command: Command)
}
