package kr.wooco.woocobe.core.plan.application.port.`in`.results

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import java.time.LocalDate

data class PlanResult(
    val id: Long,
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
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
    ) {
        companion object {
            fun of(
                order: Int,
                place: Place,
            ): PlanPlaceResult =
                PlanPlaceResult(
                    order = order,
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

    companion object {
        fun of(
            plan: Plan,
            places: List<Place>,
        ): PlanResult {
            val placeMap = places.associateBy { it.id }
            return PlanResult(
                id = plan.id,
                title = plan.title,
                contents = plan.contents,
                primaryRegion = plan.region.primaryRegion,
                secondaryRegion = plan.region.secondaryRegion,
                visitDate = plan.visitDate,
                places = plan.places.map { planPlace ->
                    val place = requireNotNull(placeMap[planPlace.placeId])
                    PlanPlaceResult.of(planPlace.order, place)
                },
            )
        }

        fun listOf(
            plans: List<Plan>,
            places: List<Place>,
        ): List<PlanResult> = plans.map { of(it, places) }
    }
}
