package kr.wooco.woocobe.placereview.application.port.`in`

fun interface UpdatePlaceReviewUseCase {
    data class Command(
        val placeReviewId: Long,
        val userId: Long,
        val rating: Double,
        val contents: String,
        val oneLineReviews: List<String>,
        val imageUrls: List<String>,
    )

    fun updatePlaceReview(command: Command)
}
