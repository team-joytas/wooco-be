package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

data class PlaceDetailResponse(
    val placeId: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
    val phoneNumber: String,
    val thumbnailUrl: String,
    val placeOnLineReviewStats: List<PlaceOneLineReviewStatDetailResponse>,
) {
    companion object {
        fun of(
            place: Place,
            placeOneLineReviewStats: List<PlaceOneLineReviewStat>,
        ): PlaceDetailResponse =
            PlaceDetailResponse(
                placeId = place.id,
                name = place.name,
                latitude = place.latitude,
                longitude = place.longitude,
                address = place.address,
                kakaoMapPlaceId = place.kakaoMapPlaceId,
                averageRating = place.averageRating,
                reviewCount = place.reviewCount,
                phoneNumber = place.phoneNumber,
                thumbnailUrl = place.thumbnailUrl,
                placeOnLineReviewStats = PlaceOneLineReviewStatDetailResponse.listFrom(placeOneLineReviewStats),
            )
    }
}
