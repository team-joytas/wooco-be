package kr.wooco.woocobe.core.place.application.port.`in`.result

import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReviewStat

data class PlaceResult(
    val placeId: Long,
    val placeName: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoPlaceId: String,
    var averageRating: Double,
    var reviewCount: Long,
    val phoneNumber: String,
    var thumbnailUrl: String,
    val placeOneLineReviewStats: List<PlaceOneLineReviewStatResult>,
) {
    companion object {
        fun of(
            place: Place,
            placeOneLineReviewStats: List<PlaceOneLineReviewStat>,
        ): PlaceResult =
            PlaceResult(
                placeId = place.id,
                placeName = place.name,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                kakaoPlaceId = place.kakaoPlaceId,
                averageRating = place.averageRating,
                reviewCount = place.reviewCount,
                phoneNumber = place.phoneNumber,
                thumbnailUrl = place.thumbnailUrl,
                placeOneLineReviewStats = PlaceOneLineReviewStatResult.listFrom(placeOneLineReviewStats),
            )

        fun listOf(
            place: List<Place>,
            placeOneLineReviewStats: List<PlaceOneLineReviewStat>,
        ): List<PlaceResult> = place.map { of(it, placeOneLineReviewStats) }
    }

    data class PlaceOneLineReviewStatResult(
        val contents: String,
        val count: Long,
    ) {
        companion object {
            fun listFrom(stats: List<PlaceOneLineReviewStat>): List<PlaceOneLineReviewStatResult> =
                stats.map {
                    PlaceOneLineReviewStatResult(
                        contents = it.contents.value,
                        count = it.count,
                    )
                }
        }
    }
}
