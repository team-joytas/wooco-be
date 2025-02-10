package kr.wooco.woocobe.placereview.application.port.`in`.result

import kr.wooco.woocobe.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceReviewResult(
    val placeReviewId: Long,
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
        fun of(
            placeReview: PlaceReview,
            user: User,
        ): PlaceReviewResult =
            PlaceReviewResult(
                placeReviewId = placeReview.id,
                writerId = user.id,
                writerName = user.profile.name,
                writerProfileUrl = user.profile.profileUrl,
                contents = placeReview.contents,
                rating = placeReview.rating,
                oneLineReviews = placeReview.oneLineReviews.map { it.value },
                reviewImageUrls = placeReview.imageUrls,
                createdAt = placeReview.writeDateTime,
            )

        fun listOf(
            placeReviews: List<PlaceReview>,
            writers: List<User>,
        ): List<PlaceReviewResult> {
            val writerMap = writers.associateBy { it.id }

            return placeReviews.map { placeReview ->
                val writer = writerMap[placeReview.userId]!!

                PlaceReviewResult(
                    placeReviewId = placeReview.id,
                    writerId = writer.id,
                    writerName = writer.profile.name,
                    writerProfileUrl = writer.profile.profileUrl,
                    contents = placeReview.contents,
                    rating = placeReview.rating,
                    oneLineReviews = placeReview.oneLineReviews.map { it.value },
                    reviewImageUrls = placeReview.imageUrls,
                    createdAt = placeReview.writeDateTime,
                )
            }
        }
    }
}
