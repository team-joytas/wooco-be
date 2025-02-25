package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewDeleteEvent(
    val placeId: Long,
    val rating: Double,
) {
    companion object {
        fun of(
            placeId: Long,
            rating: Double,
        ): PlaceReviewDeleteEvent =
            PlaceReviewDeleteEvent(
                placeId = placeId,
                rating = rating,
            )
    }
}
