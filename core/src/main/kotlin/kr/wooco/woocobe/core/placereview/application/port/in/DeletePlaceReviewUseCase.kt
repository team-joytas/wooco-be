package kr.wooco.woocobe.core.placereview.application.port.`in`

fun interface DeletePlaceReviewUseCase {
    data class Command(
        val userId: Long,
        val placeReviewId: Long,
    )

    fun deletePlaceReview(command: Command)
}
