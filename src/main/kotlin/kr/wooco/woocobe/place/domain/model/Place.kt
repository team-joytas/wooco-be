package kr.wooco.woocobe.place.domain.model

class Place(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    var averageRating: Double, // 평균 별점 로직 고려 중
    var reviewCount: Long,
    // 한줄평 통계 로직 고려 중
) {
    fun addReview(newRating: Double) {
        averageRating = ((averageRating * reviewCount) + newRating) / (reviewCount + 1)
        reviewCount++
    }

    fun updateReview(
        oldRating: Double,
        newRating: Double,
    ) {
        if (reviewCount > 0) {
            averageRating += (newRating - oldRating) / reviewCount
        }
    }

    fun deleteReview(oldRating: Double) {
        if (reviewCount > 1) {
            averageRating = ((averageRating * reviewCount) - oldRating) / (reviewCount - 1)
            reviewCount--
        } else {
            averageRating = 0.0
            reviewCount = 0
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
