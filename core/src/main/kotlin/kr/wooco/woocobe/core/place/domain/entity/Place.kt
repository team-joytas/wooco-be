package kr.wooco.woocobe.core.place.domain.entity

data class Place(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
    val phoneNumber: String,
    val thumbnailUrl: String,
) {
    init {
        require(reviewCount >= 0) { "리뷰 수는 0 미만으로 설정할 수 없습니다." }
    }

    fun increaseReviewCounts(): Place = copy(reviewCount = reviewCount + 1)

    fun decreaseReviewCounts(): Place = copy(reviewCount = reviewCount - 1)

    fun processPlaceStats(
        currentReviewRate: Double,
        reviewRate: Double,
        reviewCountOffset: Long,
    ): Place {
        val newAverageRating = when (reviewCount) {
            0L -> 0.0
            else -> ((averageRating * (reviewCount - reviewCountOffset)) - currentReviewRate + reviewRate) / reviewCount
        }
        return copy(averageRating = newAverageRating)
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
            )
    }
}
