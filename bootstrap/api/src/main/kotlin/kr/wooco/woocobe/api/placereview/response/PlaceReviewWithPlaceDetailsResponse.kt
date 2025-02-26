package kr.wooco.woocobe.api.placereview.response

import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithPlaceResult
import java.time.LocalDateTime

data class PlaceReviewWithPlaceDetailsResponse(
    val id: Long,
    val placeName: String,
    val rating: Double,
    val contents: String,
    val createdAt: LocalDateTime,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    companion object {
        fun listFrom(placeReviewWithPlaceResults: List<PlaceReviewWithPlaceResult>): List<PlaceReviewWithPlaceDetailsResponse> =
            placeReviewWithPlaceResults.map {
                PlaceReviewWithPlaceDetailsResponse(
                    id = it.placeReviewId,
                    placeName = it.placeName,
                    rating = it.rating,
                    contents = it.contents,
                    createdAt = it.createdAt,
                    oneLineReviews = it.oneLineReviews.map { oneLineReview -> oneLineReview },
                    imageUrls = it.reviewImageUrls,
                )
            }
    }
}
