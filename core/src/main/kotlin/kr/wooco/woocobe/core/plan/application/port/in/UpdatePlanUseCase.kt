package kr.wooco.woocobe.core.plan.application.port.`in`

import java.time.LocalDate

fun interface UpdatePlanUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
        val title: String,
        val contents: String,
        val primaryRegion: String,
        val secondaryRegion: String,
        val visitDate: LocalDate,
        val placeIds: List<Long>,
    )

    fun updatePlan(command: Command)
}
