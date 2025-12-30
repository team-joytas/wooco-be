package kr.wooco.woocobe.core.calendar.schedule.application.port.`in`

import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.results.PlanResult
import java.time.LocalDate

fun interface ReadAllPlanByDateUseCase {
    data class Query(
        val userId: Long,
        val date: LocalDate,
    )

    fun readAllPlanByDate(query: Query): List<PlanResult>
}
