package kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.results

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.dto.PlanView
import kr.wooco.woocobe.core.place.domain.entity.Place
import java.time.LocalDate

data class PlanResult(
    val id: Long,
    val title: String,
    val visitDate: LocalDate,
    val group: GroupResult,
    val places: List<PlanPlaceResult>,
) {
    data class PlanPlaceResult(
        val order: Int,
        val id: Long,
        val name: String,
        val latitude: Double,
        val longitude: Double,
        val address: String,
        val thumbnailUrl: String,
        val kakaoPlaceId: String,
        val averageRating: Double,
        val reviewCount: Long,
    )

    companion object {
        fun of(
            group: GroupResult,
            planView: PlanView,
            places: List<Place>,
        ): PlanResult {
            val placeMap = places.associateBy { it.id }
            return of(group, planView, placeMap)
        }

        fun of(
            group: GroupResult,
            planView: PlanView,
            placeMap: Map<Long, Place>,
        ): PlanResult {
            return PlanResult(
                id = planView.id,
                title = planView.title,
                visitDate = planView.visitDate,
                group = group,
                places = toPlanPlaceResults(planView, placeMap),
            )
        }

        fun listOf(
            groups: Map<Long, GroupResult>,
            planViews: List<PlanView>,
            placeMap: Map<Long, Place>,
        ): List<PlanResult> {
            return planViews.mapNotNull { planView ->
                val group = groups[planView.groupId] ?: return@mapNotNull null
                of(group, planView, placeMap)
            }
        }

        private fun toPlanPlaceResults(
            planView: PlanView,
            placeMap: Map<Long, Place>,
        ): List<PlanPlaceResult> {
            return planView.places.mapNotNull { planPlace ->
                val place = placeMap[planPlace.placeId] ?: return@mapNotNull null

                PlanPlaceResult(
                    order = planPlace.order,
                    id = place.id,
                    name = place.name,
                    latitude = place.latitude,
                    longitude = place.longitude,
                    address = place.address,
                    thumbnailUrl = place.thumbnailUrl,
                    kakaoPlaceId = place.kakaoPlaceId,
                    averageRating = place.averageRating,
                    reviewCount = place.reviewCount,
                )
            }
        }
    }
}
