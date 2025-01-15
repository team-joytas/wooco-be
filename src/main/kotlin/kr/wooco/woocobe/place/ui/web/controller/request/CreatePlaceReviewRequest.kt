package kr.wooco.woocobe.place.ui.web.controller.request

import kr.wooco.woocobe.place.domain.usecase.AddPlaceReviewInput

data class CreatePlaceReviewRequest(
    val rating: Double,
    val content: String,
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
            content = content,
            oneLineReviews = oneLineReviews,
            imageUrls = imageUrls,
        )
}
