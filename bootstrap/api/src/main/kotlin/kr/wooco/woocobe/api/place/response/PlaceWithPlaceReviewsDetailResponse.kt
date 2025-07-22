package kr.wooco.woocobe.api.place.response

import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceWithPlaceReviewsResult
import java.time.LocalDateTime

data class PlaceWithPlaceReviewsDetailResponse(
    val place: PlaceDetailResponse,
    val placeReviews: List<PlaceReviewDetailResponse>,
) {
    data class PlaceReviewDetailResponse(
        val id: Long,
        val writer: PlaceReviewWriterResponse,
        val rating: Double,
        val contents: String,
        val createdAt: LocalDateTime,
        val oneLineReviews: List<String>,
        val imageUrls: List<String>,
    )

    data class PlaceReviewWriterResponse(
        val id: Long,
        val name: String,
        val profileUrl: String,
    )

    companion object {
        fun from(placeWithPlaceReviewsResult: PlaceWithPlaceReviewsResult) =
            PlaceWithPlaceReviewsDetailResponse(
                place = PlaceDetailResponse.from(placeWithPlaceReviewsResult.place),
                placeReviews = placeWithPlaceReviewsResult.placeReviews.map { placeReview ->
                    PlaceReviewDetailResponse(
                        id = placeReview.placeReviewId,
                        writer = PlaceReviewWriterResponse(
                            id = placeReview.writer.id,
                            name = placeReview.writer.name,
                            profileUrl = placeReview.writer.profileUrl,
                        ),
                        rating = placeReview.rating,
                        contents = placeReview.contents,
                        createdAt = placeReview.createdAt,
                        oneLineReviews = placeReview.oneLineReviews,
                        imageUrls = placeReview.reviewImageUrls,
                    )
                },
            )
    }
}
