package kr.wooco.woocobe.core.calendar.schedule.application.port.`in`

interface DeletePlanUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
    )

    fun deletePlan(command: Command): Long
}
