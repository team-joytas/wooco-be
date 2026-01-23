package kr.wooco.woocobe.core.calendar.application.port.out.dto

import java.time.LocalDate

/**
 * 캘린더 Read Model
 *
 * @author Junseoparkk
 */
data class CalendarView(
    val planId: Long,
    val groupId: Long,
    val groupName: String,
    val groupSize: Int,
    val title: String,
    val visitDate: LocalDate,
    val places: List<CalendarPlaceView>,
) {
    data class CalendarPlaceView(
        val id: Long,
        val name: String,
        val address: String,
        val thumbnailUrl: String,
    )
}
