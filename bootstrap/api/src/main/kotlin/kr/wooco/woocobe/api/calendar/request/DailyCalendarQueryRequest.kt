package kr.wooco.woocobe.api.calendar.request

import kr.wooco.woocobe.core.calendar.application.port.`in`.ReadDailyCalendarUseCase
import java.time.LocalDate

data class DailyCalendarQueryRequest(
    val date: LocalDate,
    val groupIds: List<Long>?,
) {
    fun toQuery(userId: Long): ReadDailyCalendarUseCase.Query =
        ReadDailyCalendarUseCase.Query(
            userId = userId,
            date = date,
            groupIds = groupIds,
        )
}
