package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewDeleteEvent(
    val placeId: Long,
) {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewDeleteEvent =
            PlaceReviewDeleteEvent(
                placeId = placeReview.placeId,
            )
    }
}
