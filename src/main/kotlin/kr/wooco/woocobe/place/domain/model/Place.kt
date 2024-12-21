package kr.wooco.woocobe.place.domain.model

import kr.wooco.woocobe.user.domain.model.User

class Place(
    val id: Long,
    val user: User,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    var averageRating: Double, // 평균 별점 로직 고려 중
    var reviewCount: Long,
    // 한줄평 통계 로직 고려 중
) {
    fun increaseReviewCount() =
        apply {
            reviewCount++
        }

    fun decreaseReviewCount() =
        apply {
            reviewCount--
        }

    fun updateAverageRating(rating: Double) =
        apply {
            averageRating = (averageRating * reviewCount + rating) / (reviewCount)
        }

    companion object {
        fun register(
            user: User,
            name: String,
            latitude: Double,
            longitude: Double,
            address: String,
            kakaoMapPlaceId: String,
        ): Place =
            Place(
                id = 0L,
                user = user,
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
