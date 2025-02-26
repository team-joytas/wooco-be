package kr.wooco.woocobe.core.placereview.application.port.`in`.result

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceReviewWithPlaceResult(
    val placeReviewId: Long,
    val placeName: String,
    val writerId: Long,
    val writerName: String,
    val writerProfileUrl: String,
    val contents: String,
    val rating: Double,
    val oneLineReviews: List<String>,
    val reviewImageUrls: List<String>,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun listOf(
            placeReviews: List<PlaceReview>,
            placeOneLineReviews: List<PlaceOneLineReview>,
            writer: User,
            places: List<Place>,
        ): List<PlaceReviewWithPlaceResult> {
            val oneLineReviewsMap = placeOneLineReviews.groupBy { it.placeReviewId }
            val placeMap = places.associateBy { it.id }

            return placeReviews.map { placeReview ->
                val oneLineReviews = oneLineReviewsMap[placeReview.id] ?: emptyList()
                val place = placeMap[placeReview.placeId]!!

                PlaceReviewWithPlaceResult(
                    placeReviewId = placeReview.id,
                    placeName = place.name,
                    writerId = writer.id,
                    writerName = writer.profile.name,
                    writerProfileUrl = writer.profile.profileUrl,
                    contents = placeReview.contents,
                    rating = placeReview.rating,
                    oneLineReviews = oneLineReviews.map { it.contents.value },
                    reviewImageUrls = placeReview.imageUrls,
                    createdAt = placeReview.writeDateTime,
                )
            }
        }
    }
}
