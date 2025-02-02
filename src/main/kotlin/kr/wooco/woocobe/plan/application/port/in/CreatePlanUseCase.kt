package kr.wooco.woocobe.plan.application.port.`in`

import java.time.LocalDate

fun interface CreatePlanUseCase {
    data class Command(
        val userId: Long,
        val title: String,
        val contents: String,
        val primaryRegion: String,
        val secondaryRegion: String,
        val visitDate: LocalDate,
        val placeIds: List<Long>,
        val categories: List<String>,
    )

    fun createPlan(command: Command): Long
}
