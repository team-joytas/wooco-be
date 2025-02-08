package kr.wooco.woocobe.placereview.adapter.`in`.web.request

import kr.wooco.woocobe.placereview.application.port.`in`.UpdatePlaceReviewUseCase

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
