package kr.wooco.woocobe.core.placereview.application.port.`in`

import kr.wooco.woocobe.core.placereview.domain.command.DeletePlaceReviewCommand

fun interface DeletePlaceReviewUseCase {
    data class Command(
        val userId: Long,
        val placeReviewId: Long,
    ) {
        fun toDeleteCommand(): DeletePlaceReviewCommand =
            DeletePlaceReviewCommand(
                userId = userId,
            )
    }

    fun deletePlaceReview(command: Command)
}
