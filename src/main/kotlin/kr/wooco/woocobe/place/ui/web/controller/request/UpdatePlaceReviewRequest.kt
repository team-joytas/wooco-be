package kr.wooco.woocobe.place.ui.web.controller.request

import kr.wooco.woocobe.place.domain.usecase.UpdatePlaceReviewInput

data class UpdatePlaceReviewRequest(
    val rating: Double,
    val contents: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    fun toCommand(
        userId: Long,
        placeReviewId: Long,
    ): UpdatePlaceReviewInput =
        UpdatePlaceReviewInput(
            userId = userId,
            placeReviewId = placeReviewId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )
}
