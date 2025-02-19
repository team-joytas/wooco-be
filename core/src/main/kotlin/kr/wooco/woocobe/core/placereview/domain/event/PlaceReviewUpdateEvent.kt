package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewUpdateEvent(
    val placeId: Long,
    val oldRating: Double,
    val newRating: Double,
)
