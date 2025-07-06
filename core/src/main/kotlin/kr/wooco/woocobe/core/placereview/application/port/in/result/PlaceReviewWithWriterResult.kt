package kr.wooco.woocobe.core.placereview.application.port.`in`.result

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
            user: User,
        ): PlaceReviewWithWriterResult =
            PlaceReviewWithWriterResult(
                placeReviewId = placeReview.id,
                writerId = user.id,
                writerName = user.profile.name,
                writerProfileUrl = user.profile.profileUrl,
                contents = placeReview.contents.contents,
                rating = placeReview.rating.score,
                oneLineReviews = placeReview.oneLineReviews.map { it.contents },
                reviewImageUrls = placeReview.imageUrls,
                createdAt = placeReview.writeDateTime,
            )

        fun listOf(
            placeReviews: List<PlaceReview>,
            writers: List<User>,
        ): List<PlaceReviewWithWriterResult> {
            val writerMap = writers.associateBy { it.id }
            return placeReviews.map { placeReview ->
                val writer = writerMap[placeReview.userId]!!
                PlaceReviewWithWriterResult(
                    placeReviewId = placeReview.id,
                    writerId = writer.id,
                    writerName = writer.profile.name,
                    writerProfileUrl = writer.profile.profileUrl,
                    contents = placeReview.contents.contents,
                    rating = placeReview.rating.score,
                    oneLineReviews = placeReview.oneLineReviews.map { it.contents },
                    reviewImageUrls = placeReview.imageUrls,
                    createdAt = placeReview.writeDateTime,
                )
            }
        }
    }
}
