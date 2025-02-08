package kr.wooco.woocobe.place.domain.entity

data class Place(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    var averageRating: Double,
    var reviewCount: Long,
    val phoneNumber: String,
    var thumbnailUrl: String,
    val placeOneLineReviewStats: List<PlaceOneLineReviewStat>,
) {
    // TODO: 한줄평 통계 로직 필요

    fun increaseReviewCounts() {
        reviewCount++
    }

    fun decreaseReviewCounts() {
        if (reviewCount > 0) {
            reviewCount--
        }
    }

    fun processPlaceStats(
        currentReviewRate: Double,
        reviewRate: Double,
    ) {
        averageRating = when (reviewCount) {
            0L -> 0.0
            else -> ((averageRating * reviewCount) - currentReviewRate + reviewRate) / reviewCount
        }
    }

    companion object {
        fun create(
            name: String,
            latitude: Double,
            longitude: Double,
            address: String,
            kakaoPlaceId: String,
            phoneNumber: String,
        ): Place =
            Place(
                id = 0L,
                name = name,
                latitude = latitude,
                longitude = longitude,
                address = address,
                kakaoPlaceId = kakaoPlaceId,
                averageRating = 0.0,
                reviewCount = 0,
                phoneNumber = phoneNumber,
                thumbnailUrl = "",
                placeOneLineReviewStats = emptyList(),
            )
    }
}
