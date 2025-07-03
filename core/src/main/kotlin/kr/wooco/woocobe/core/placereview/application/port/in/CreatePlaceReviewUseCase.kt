package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.domain.command.CreatePlaceReviewCommand
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

fun interface CreatePlaceReviewUseCase {
    data class Command(
        val userId: Long,
        val placeId: Long,
        val rating: Double,
        val contents: String,
        val oneLineReviews: List<String>,
        val imageUrls: List<String>,
    ) {
        fun toCreateCommand(): CreatePlaceReviewCommand =
            CreatePlaceReviewCommand(
                userId = userId,
                placeId = placeId,
                rating = PlaceReviewRating(rating),
                contents = contents,
                oneLineReviews = oneLineReviews.map { PlaceOneLineReview(it) },
                imageUrls = imageUrls,
            )
    }

    fun createPlaceReview(command: Command): Long
}
