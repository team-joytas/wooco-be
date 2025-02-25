package kr.wooco.woocobe.core.placereview.domain.event

data class PlaceReviewUpdateEvent(
    val placeId: Long,
    val oldRating: Double,
    val newRating: Double,
) {
    companion object {
        fun of(
            placeId: Long,
            oldRating: Double,
            newRating: Double,
        ): PlaceReviewUpdateEvent =
            PlaceReviewUpdateEvent(
                placeId = placeId,
                oldRating = oldRating,
                newRating = newRating,
            )
    }
}
