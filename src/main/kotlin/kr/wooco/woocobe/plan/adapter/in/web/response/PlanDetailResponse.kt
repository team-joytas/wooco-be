package kr.wooco.woocobe.plan.adapter.`in`.web.response

import kr.wooco.woocobe.plan.application.port.`in`.results.PlanPlaceResult
import kr.wooco.woocobe.plan.application.port.`in`.results.PlanResult
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
        fun from(planResult: PlanResult): PlanDetailResponse {
            val places = planResult.places
            val placeMap = places.associateBy { it.id }
            return fromPlan(planResult, placeMap)
        }

        fun listOf(planResults: List<PlanResult>): List<PlanDetailResponse> = planResults.map { plan -> from(plan) }

        private fun fromPlan(
            plan: PlanResult,
            placeMap: Map<Long, PlanPlaceResult>,
        ): PlanDetailResponse =
            PlanDetailResponse(
                id = plan.id,
                title = plan.title,
                contents = plan.contents,
                primaryRegion = plan.primaryRegion,
                secondaryRegion = plan.secondaryRegion,
                visitDate = plan.visitDate,
                places = plan.places.map { planPlace ->
                    val place = requireNotNull(placeMap[planPlace.id])
                    PlanPlaceResponse.of(planPlace.order, place)
                },
                categories = plan.categories,
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
            place: PlanPlaceResult,
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
