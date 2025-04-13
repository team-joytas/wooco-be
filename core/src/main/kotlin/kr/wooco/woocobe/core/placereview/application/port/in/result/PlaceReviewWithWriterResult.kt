package kr.wooco.woocobe.core.placereview.application.port.`in`.result

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.user.domain.entity.User
import java.time.LocalDateTime

data class PlaceReviewWithWriterResult(
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
            placeOneLineReviews: List<PlaceOneLineReview>,
            user: User,
        ): PlaceReviewWithWriterResult =
            PlaceReviewWithWriterResult(
                placeReviewId = placeReview.id,
                writerId = user.id,
                writerName = user.profile.name,
                writerProfileUrl = user.profile.profileUrl,
                contents = placeReview.contents,
                rating = placeReview.rating.score,
                oneLineReviews = placeOneLineReviews.map { it.contents.value },
                reviewImageUrls = placeReview.imageUrls,
                createdAt = placeReview.writeDateTime,
            )

        fun listOf(
            placeReviews: List<PlaceReview>,
            placeOneLineReviews: List<PlaceOneLineReview>,
            writers: List<User>,
        ): List<PlaceReviewWithWriterResult> {
            val writerMap = writers.associateBy { it.id }
            val oneLineReviewsMap = placeOneLineReviews.groupBy { it.placeReviewId }
            return placeReviews.map { placeReview ->
                val writer = writerMap[placeReview.userId]!!
                val oneLineReviews = oneLineReviewsMap[placeReview.id] ?: emptyList()
                PlaceReviewWithWriterResult(
                    placeReviewId = placeReview.id,
                    writerId = writer.id,
                    writerName = writer.profile.name,
                    writerProfileUrl = writer.profile.profileUrl,
                    contents = placeReview.contents,
                    rating = placeReview.rating.score,
                    oneLineReviews = oneLineReviews.map { it.contents.value },
                    reviewImageUrls = placeReview.imageUrls,
                    createdAt = placeReview.writeDateTime,
                )
            }
        }
    }
}
