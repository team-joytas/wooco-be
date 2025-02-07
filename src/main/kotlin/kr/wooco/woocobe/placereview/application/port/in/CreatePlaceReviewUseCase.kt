package kr.wooco.woocobe.placereview.application.port.`in`

fun interface CreatePlaceReviewUseCase {
    data class Command(
        val userId: Long,
        val placeId: Long,
        val rating: Double,
        val contents: String,
        val oneLineReviews: List<String>,
        val imageUrls: List<String>,
    )

    fun createPlaceReview(command: Command): Long
}
