package kr.wooco.woocobe.api.calendar.response

import kr.wooco.woocobe.core.calendar.application.port.out.dto.CalendarView
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
    ) {
        companion object {
            fun from(view: CalendarView.CalendarPlaceView): CalendarPlaceResponse =
                CalendarPlaceResponse(
                    id = view.id,
                    name = view.name,
                    address = view.address,
                    thumbnailUrl = view.thumbnailUrl,
                )

            fun listFrom(views: List<CalendarView.CalendarPlaceView>): List<CalendarPlaceResponse> =
                views.map { from(it) }
        }
    }

    companion object {
        fun from(view: CalendarView): CalendarDetailResponse =
            CalendarDetailResponse(
                planId = view.planId,
                groupId = view.groupId,
                groupName = view.groupName,
                groupSize = view.groupSize,
                title = view.title,
                visitDate = view.visitDate,
                places = CalendarPlaceResponse.listFrom(view.places),
            )

        fun listFrom(views: List<CalendarView>): List<CalendarDetailResponse> =
            views.map { from(it) }
    }
}
