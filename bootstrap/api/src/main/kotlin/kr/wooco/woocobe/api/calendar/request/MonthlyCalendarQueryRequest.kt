package kr.wooco.woocobe.api.calendar.request

import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadMonthlyCalendarUseCase

data class MonthlyCalendarQueryRequest(
    val year: Int,
    val month: Int,
    val groupIds: List<Long>?,
) {
    fun toQuery(userId: Long): ReadMonthlyCalendarUseCase.Query =
        ReadMonthlyCalendarUseCase.Query(
            userId = userId,
            year = year,
            month = month,
            groupIds = groupIds,
        )
}
