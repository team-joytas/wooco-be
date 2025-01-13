package kr.wooco.woocobe.place.domain.model

class Place(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    var averageRating: Double,
    var reviewCount: Long,
) {
    fun increaseReviewCounts() {
        reviewCount++
    }

    fun decreaseReviewCounts() {
        if (reviewCount > 1) {
            reviewCount--
        } else {
            averageRating = 0.0
            reviewCount = 0
        }
    }

    fun processPlaceStats(
        currentReviewRate: Double,
        reviewRate: Double,
    ) {
        if (reviewCount > 0) {
            averageRating =
                ((averageRating * reviewCount) - currentReviewRate + reviewRate) / reviewCount
        }
    }

    companion object {
        fun register(
            name: String,
            latitude: Double,
            longitude: Double,
            address: String,
            kakaoMapPlaceId: String,
        ): Place =
            Place(
                id = 0L,
                name = name,
                latitude = latitude,
                longitude = longitude,
                address = address,
                kakaoMapPlaceId = kakaoMapPlaceId,
                averageRating = 0.0,
                reviewCount = 0,
            )
    }
}
