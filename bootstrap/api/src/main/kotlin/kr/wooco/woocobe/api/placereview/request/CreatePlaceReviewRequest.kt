package kr.wooco.woocobe.api.placereview.request

import kr.wooco.woocobe.core.placereview.application.port.`in`.CreatePlaceReviewUseCase

data class CreatePlaceReviewRequest(
    val rating: Double,
    val contents: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    fun toCommand(
        userId: Long,
        placeId: Long,
    ): CreatePlaceReviewUseCase.Command =
        CreatePlaceReviewUseCase.Command(
            userId = userId,
            placeId = placeId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )
}
