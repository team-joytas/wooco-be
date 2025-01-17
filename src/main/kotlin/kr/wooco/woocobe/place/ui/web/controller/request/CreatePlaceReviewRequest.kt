package kr.wooco.woocobe.place.ui.web.controller.request

import kr.wooco.woocobe.place.domain.usecase.AddPlaceReviewInput

data class CreatePlaceReviewRequest(
    val rating: Double,
    val contents: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
) {
    fun toCommand(
        userId: Long,
        placeId: Long,
    ): AddPlaceReviewInput =
        AddPlaceReviewInput(
            userId = userId,
            placeId = placeId,
            rating = rating,
            contents = contents,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )
}
