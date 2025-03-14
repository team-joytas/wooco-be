package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewCreateEvent(
    val placeId: Long,
) {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewCreateEvent =
            PlaceReviewCreateEvent(
                placeId = placeReview.placeId,
            )
    }
}
