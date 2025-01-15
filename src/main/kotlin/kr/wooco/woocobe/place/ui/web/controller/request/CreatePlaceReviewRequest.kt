package kr.wooco.woocobe.place.ui.web.controller.request

data class CreatePlaceReviewRequest(
    val rating: Double,
    val content: String,
    val oneLineReviews: List<String>,
    val imageUrls: List<String>,
)
