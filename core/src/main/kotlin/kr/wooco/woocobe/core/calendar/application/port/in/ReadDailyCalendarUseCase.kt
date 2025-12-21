package kr.wooco.woocobe.core.calendar.application.port.`in`

import kr.wooco.woocobe.core.calendar.application.port.out.dto.CalendarView
import java.time.LocalDate

fun interface ReadDailyCalendarUseCase {
    data class Query(
        val userId: Long,
        val date: LocalDate,
        val groupIds: List<Long>?,
    )

    fun readDailyCalendar(query: Query): List<CalendarView>
}
