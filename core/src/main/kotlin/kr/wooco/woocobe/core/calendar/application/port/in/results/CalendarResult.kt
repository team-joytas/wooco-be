package kr.wooco.woocobe.core.calendar.application.port.`in`.results

import kr.wooco.woocobe.core.calendar.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.place.domain.entity.Place
import java.time.LocalDate

data class CalendarResult(
    val planId: Long,
    val groupId: Long,
    val groupName: String,
    val groupSize: Int,
    val title: String,
    val visitDate: LocalDate,
    val places: List<CalendarPlaceResult>
) {
    data class CalendarPlaceResult(
        val id: Long,
        val name: String,
        val address: String,
        val thumbnailUrl: String,
        val order: Int,
    )

    companion object {
        fun listFrom(
            planViews: List<PlanView>,
            groupMap: Map<Long, GroupView>,
            placeMap: Map<Long, Place>,
        ): List<CalendarResult> =
            planViews.mapNotNull { planView ->
                val group = groupMap[planView.groupId] ?: return@mapNotNull null

                val places = planView.places.mapNotNull { placeView ->
                    val place = placeMap[placeView.placeId] ?: return@mapNotNull null
                    CalendarPlaceResult(
                        id = place.id,
                        name = place.name,
                        address = place.address,
                        thumbnailUrl = place.thumbnailUrl,
                        order = placeView.order,
                    )
                }

                CalendarResult(
                    planId = planView.id,
                    groupId = planView.groupId,
                    groupName = group.name,
                    groupSize = group.groupSize,
                    title = planView.title,
                    visitDate = planView.visitDate,
                    places = places,
                )
            }
    }
}
