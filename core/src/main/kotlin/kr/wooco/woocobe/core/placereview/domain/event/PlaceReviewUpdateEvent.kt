package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.application.port.`in`.UpdatePlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewUpdateEvent(
    val placeId: Long,
    val oldRating: Double,
    val newRating: Double,
) {
    companion object {
        fun of(
            placeReview: PlaceReview,
            command: UpdatePlaceReviewUseCase.Command,
        ): PlaceReviewUpdateEvent =
            PlaceReviewUpdateEvent(
                placeId = placeReview.id,
                oldRating = placeReview.rating,
                newRating = command.rating,
            )
    }
}
