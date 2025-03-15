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

    fun updateThumbnail(thumbnailUrl: String): Place =
        copy(
            thumbnailUrl = thumbnailUrl,
        )

    fun updateAverageRating(averageRating: Double): Place =
        copy(
            averageRating = averageRating,
        )

    fun updatePlaceReviewStats(
        averageRating: Double,
        reviewCount: Long,
    ): Place =
        copy(
            averageRating = averageRating,
            reviewCount = reviewCount,
        )

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
