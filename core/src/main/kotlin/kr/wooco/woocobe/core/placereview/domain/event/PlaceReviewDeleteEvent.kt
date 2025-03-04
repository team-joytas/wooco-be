package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewDeleteEvent(
    val placeId: Long,
    val rating: Double,
) {
    companion object {
        fun from(placeReview: PlaceReview): PlaceReviewDeleteEvent =
            PlaceReviewDeleteEvent(
                placeId = placeReview.placeId,
                rating = placeReview.rating,
            )
    }
}
