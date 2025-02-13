package kr.wooco.woocobe.api.placereview.request

import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase

data class UpdatePlaceReviewRequest(
    val rating: Double,
    val contents: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    fun toCommand(
        userId: Long,
        placeReviewId: Long,
    ): UpdatePlaceReviewUseCase.Command =
        UpdatePlaceReviewUseCase.Command(
            userId = userId,
            placeReviewId = placeReviewId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )
}
