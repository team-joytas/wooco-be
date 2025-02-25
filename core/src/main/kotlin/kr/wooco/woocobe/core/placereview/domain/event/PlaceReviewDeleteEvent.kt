package kr.wooco.woocobe.core.placereview.domain.event

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

data class PlaceReviewDeleteEvent(
    val placeId: Long,
    val rating: Double,
) {
    companion object {
        fun of(placeReview: PlaceReview): PlaceReviewDeleteEvent =
            PlaceReviewDeleteEvent(
                placeId = placeReview.id,
                rating = placeReview.rating,
            )
    }
}
