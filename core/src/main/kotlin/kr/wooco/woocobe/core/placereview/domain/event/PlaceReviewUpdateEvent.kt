package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewUpdateEvent(
    val placeId: Long,
) {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewUpdateEvent =
            PlaceReviewUpdateEvent(
                placeId = placeReview.placeId,
            )
    }
}
