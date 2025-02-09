package kr.wooco.woocobe.place.application.port.`in`.result

import kr.wooco.woocobe.place.domain.entity.Place
import kr.wooco.woocobe.place.domain.entity.PlaceOneLineReviewStat

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
        fun from(place: Place): PlaceResult =
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
                placeOneLineReviewStats = PlaceOneLineReviewStatResult.listFrom(place.placeOneLineReviewStats),
            )

        fun listFrom(place: List<Place>): List<PlaceResult> =
            place.map {
                PlaceResult(
                    placeId = it.id,
                    placeName = it.name,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    address = it.address,
                    kakaoPlaceId = it.kakaoPlaceId,
                    averageRating = it.averageRating,
                    reviewCount = it.reviewCount,
                    phoneNumber = it.phoneNumber,
                    thumbnailUrl = it.thumbnailUrl,
                    placeOneLineReviewStats = PlaceOneLineReviewStatResult.listFrom(it.placeOneLineReviewStats),
                )
            }
    }

    data class PlaceOneLineReviewStatResult(
        val contents: String,
        val count: Long,
    ) {
        companion object {
            fun listFrom(stats: List<PlaceOneLineReviewStat>): List<PlaceOneLineReviewStatResult> =
                stats.map {
                    PlaceOneLineReviewStatResult(
                        contents = it.contents,
                        count = it.count,
                    )
                }
        }
    }
}
