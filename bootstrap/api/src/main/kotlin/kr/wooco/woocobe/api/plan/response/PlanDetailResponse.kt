package kr.wooco.woocobe.api.plan.response

import kr.wooco.woocobe.core.plan.application.port.`in`.results.PlanResult
import java.time.LocalDate

data class PlanDetailResponse(
    val id: Long,
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val places: List<PlanPlaceResponse>,
) {
    companion object {
        fun from(planResult: PlanResult): PlanDetailResponse {
            val places = planResult.places
            val placeMap = places.associateBy { it.id }
            return PlanDetailResponse(
                id = planResult.id,
                title = planResult.title,
                contents = planResult.contents,
                primaryRegion = planResult.primaryRegion,
                secondaryRegion = planResult.secondaryRegion,
                visitDate = planResult.visitDate,
                places = planResult.places.map { planPlace ->
                    val place = requireNotNull(placeMap[planPlace.id])
                    PlanPlaceResponse.of(planPlace.order, place)
                },
            )
        }

        fun listOf(planResults: List<PlanResult>): List<PlanDetailResponse> = planResults.map { plan -> from(plan) }
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
    val kakaoPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
) {
    companion object {
        fun of(
            order: Int,
            place: PlanResult.PlanPlaceResult,
        ): PlanPlaceResponse =
            PlanPlaceResponse(
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
