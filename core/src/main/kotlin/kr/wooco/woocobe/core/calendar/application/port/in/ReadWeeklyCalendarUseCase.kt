package kr.wooco.woocobe.core.calendar.application.port.`in`

import kr.wooco.woocobe.core.calendar.application.port.out.dto.CalendarView

fun interface ReadWeeklyCalendarUseCase {
    data class Query(
        val userId: Long,
        val year: Int,
        val month: Int,
        val week: Int,
        val groupIds: List<Long>?,
    )

    fun readWeeklyCalendar(query: Query): List<CalendarView>
}
