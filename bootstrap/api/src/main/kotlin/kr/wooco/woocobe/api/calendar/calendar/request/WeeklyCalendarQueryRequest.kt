package kr.wooco.woocobe.api.calendar.calendar.request

import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadWeeklyCalendarUseCase

data class WeeklyCalendarQueryRequest(
    val year: Int,
    val month: Int,
    val week: Int,
    val groupIds: List<Long>?,
) {
    fun toQuery(userId: Long): ReadWeeklyCalendarUseCase.Query =
        ReadWeeklyCalendarUseCase.Query(
            userId = userId,
            year = year,
            month = month,
            week = week,
            groupIds = groupIds,
        )
}
