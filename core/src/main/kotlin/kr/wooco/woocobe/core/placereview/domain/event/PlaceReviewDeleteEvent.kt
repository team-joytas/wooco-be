package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewDeleteEvent(
    val placeId: Long,
    val rating: Double,
)
