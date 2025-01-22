package kr.wooco.woocobe.plan.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.plan.domain.model.Plan
import java.time.LocalDate

data class PlanDetailResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val places: List<PlanPlaceResponse>,
    val categories: List<String>,
) {
    companion object {
        fun of(
            plan: Plan,
            places: List<Place>,
        ): PlanDetailResponse {
            val placeMap = places.associateBy { it.id }
            return fromPlan(plan, placeMap)
        }

        fun listOf(
            plans: List<Plan>,
            places: List<Place>,
        ): List<PlanDetailResponse> {
            val placeMap = places.associateBy { it.id }
            return plans.map { plan -> fromPlan(plan, placeMap) }
        }

        private fun fromPlan(
            plan: Plan,
            placeMap: Map<Long, Place>,
        ): PlanDetailResponse =
            PlanDetailResponse(
                id = plan.id,
                title = plan.title,
                contents = plan.contents,
                primaryRegion = plan.region.primaryRegion,
                secondaryRegion = plan.region.secondaryRegion,
                visitDate = plan.visitDate,
                places = plan.places.map { planPlace ->
                    val place = requireNotNull(placeMap[planPlace.placeId])
                    PlanPlaceResponse.of(planPlace.order, place)
                },
                categories = plan.categories.map { it.name },
            )
    }
}

data class PlanPlaceResponse(
    val order: Int,
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val thumbnailUrl: String,
    val kakaoMapPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
) {
    companion object {
        fun of(
            order: Int,
            place: Place,
        ): PlanPlaceResponse =
            PlanPlaceResponse(
                order = order,
                id = place.id,
                name = place.name,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                thumbnailUrl = place.thumbnailUrl,
                kakaoMapPlaceId = place.kakaoMapPlaceId,
                averageRating = place.averageRating,
                reviewCount = place.reviewCount,
            )
    }
}
