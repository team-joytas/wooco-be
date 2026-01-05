package kr.wooco.woocobe.core.calendar.application.port.`in`

import kr.wooco.woocobe.core.calendar.application.port.`in`.results.CalendarResult
import java.time.LocalDate

fun interface ReadCalendarUseCase {
    data class Query(
        val userId: Long,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val groupIds: List<Long>?,
    )

    fun readCalendar(query: Query): List<CalendarResult>
}
