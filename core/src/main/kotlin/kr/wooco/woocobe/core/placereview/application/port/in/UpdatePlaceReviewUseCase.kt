package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.domain.command.UpdatePlaceReviewCommand
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating

fun interface UpdatePlaceReviewUseCase {
    data class Command(
        val placeReviewId: Long,
        val userId: Long,
        val rating: Double,
        val content: String,
        val oneLineReviews: List<String>,
        val imageUrls: List<String>,
    ) {
        fun toUpdateCommand(): UpdatePlaceReviewCommand =
            UpdatePlaceReviewCommand(
                userId = userId,
                rating = PlaceReviewRating(rating),
                content = content,
                imageUrls = imageUrls,
            )
    }

    fun updatePlaceReview(command: Command)
}
