package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewCreateEvent(
    val placeId: Long,
    val rating: Double,
)
