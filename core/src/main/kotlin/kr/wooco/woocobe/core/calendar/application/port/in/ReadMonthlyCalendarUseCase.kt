package kr.wooco.woocobe.core.calendar.application.port.`in`

import kr.wooco.woocobe.core.calendar.application.port.out.dto.CalendarView

fun interface ReadMonthlyCalendarUseCase {
    data class Query(
        val userId: Long,
        val year: Int,
        val month: Int,
        val groupIds: List<Long>?,
    )

    fun readMonthlyCalendar(query: Query): List<CalendarView>
}
