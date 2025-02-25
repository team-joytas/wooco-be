package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewCreateEvent(
    val placeId: Long,
    val rating: Double,
) {
    companion object {
        fun of(
            placeId: Long,
            rating: Double,
        ): PlaceReviewCreateEvent =
            PlaceReviewCreateEvent(
                placeId = placeId,
                rating = rating,
            )
    }
}
