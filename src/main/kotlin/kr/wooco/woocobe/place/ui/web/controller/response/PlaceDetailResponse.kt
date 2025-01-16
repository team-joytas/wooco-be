package kr.wooco.woocobe.place.ui.web.controller.response

import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceReview
import kr.wooco.woocobe.user.domain.model.User

data class PlaceDetailResponse(
    val placeId: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val kakaoMapPlaceId: String,
    val averageRating: Double,
    val reviewCount: Long,
    val placeReviews: List<PlaceReviewDetailsResponse>,
) {
    companion object {
        fun of(
            place: Place,
            placeReviews: List<PlaceReview>,
            users: List<User>,
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
                placeReviews = PlaceReviewDetailsResponse.listOf(
                    placeReviews = placeReviews,
                    users = users,
                ),
            )
    }
}
