package kr.wooco.woocobe.api.calendar.calendar.response

import kr.wooco.woocobe.core.calendar.application.port.`in`.results.CalendarResult
import java.time.LocalDate

data class CalendarDetailResponse(
    val planId: Long,
    val groupId: Long,
    val groupName: String,
    val groupSize: Int,
    val title: String,
    val visitDate: LocalDate,
    val places: List<CalendarPlaceResponse>
) {
    data class CalendarPlaceResponse(
        val id: Long,
        val name: String,
        val address: String,
        val thumbnailUrl: String,
        val order: Int,
    ) {
        companion object {
            fun from(result: CalendarResult.CalendarPlaceResult): CalendarPlaceResponse =
                CalendarPlaceResponse(
                    id = result.id,
                    name = result.name,
                    address = result.address,
                    thumbnailUrl = result.thumbnailUrl,
                    order = result.order,
                )

            fun listFrom(results: List<CalendarResult.CalendarPlaceResult>): List<CalendarPlaceResponse> =
                results.map { from(it) }
        }
    }

    companion object {
        fun from(result: CalendarResult): CalendarDetailResponse =
            CalendarDetailResponse(
                planId = result.planId,
                groupId = result.groupId,
                groupName = result.groupName,
                groupSize = result.groupSize,
                title = result.title,
                visitDate = result.visitDate,
                places = CalendarPlaceResponse.listFrom(result.places),
            )

        fun listFrom(views: List<CalendarResult>): List<CalendarDetailResponse> =
            views.map { from(it) }
    }
}
