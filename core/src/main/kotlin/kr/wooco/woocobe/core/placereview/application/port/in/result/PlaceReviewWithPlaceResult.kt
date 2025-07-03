package kr.wooco.woocobe.core.placereview.application.port.`in`.result

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import java.time.LocalDateTime

data class PlaceReviewWithPlaceResult(
    val id: Long,
    val place: PlaceResult,
    val contents: String,
    val rating: Double,
    val oneLineReviews: List<String>,
    val reviewImageUrls: List<String>,
    val createdAt: LocalDateTime,
) {
    data class PlaceResult(
        val id: Long,
        val name: String,
    )

    companion object {
        fun listOf(
            placeReviews: List<PlaceReview>,
            places: List<Place>,
        ): List<PlaceReviewWithPlaceResult> {
            val placeMap = places.associateBy { it.id }

            return placeReviews.map { placeReview ->
                val place = placeMap[placeReview.placeId]!!

                PlaceReviewWithPlaceResult(
                    id = placeReview.id,
                    place = PlaceResult(
                        id = place.id,
                        name = place.name,
                    ),
                    contents = placeReview.contents,
                    rating = placeReview.rating.score,
                    oneLineReviews = placeReview.oneLineReviews.map { it.contents },
                    reviewImageUrls = placeReview.imageUrls,
                    createdAt = placeReview.writeDateTime,
                )
            }
        }
    }
}
