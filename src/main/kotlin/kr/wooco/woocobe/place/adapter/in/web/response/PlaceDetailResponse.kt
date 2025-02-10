package kr.wooco.woocobe.place.adapter.`in`.web.response

import kr.wooco.woocobe.place.application.port.`in`.result.PlaceResult

data class PlaceDetailResponse(
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
    val placeOneLineReviewStats: List<PlaceOneLineReviewStatDetailResponse>,
) {
    companion object {
        fun from(placeResult: PlaceResult): PlaceDetailResponse =
            PlaceDetailResponse(
                id = placeResult.placeId,
                name = placeResult.placeName,
                latitude = placeResult.latitude,
                longitude = placeResult.longitude,
                address = placeResult.address,
                kakaoPlaceId = placeResult.kakaoPlaceId,
                averageRating = placeResult.averageRating,
                reviewCount = placeResult.reviewCount,
                phoneNumber = placeResult.phoneNumber,
                thumbnailUrl = placeResult.thumbnailUrl,
                placeOneLineReviewStats = PlaceOneLineReviewStatDetailResponse.listFrom(placeResult.placeOneLineReviewStats),
            )
    }

    data class PlaceOneLineReviewStatDetailResponse(
        val contents: String,
        val count: Long,
    ) {
        companion object {
            fun listFrom(
                placeOneLineReviewStatsResult: List<PlaceResult.PlaceOneLineReviewStatResult>,
            ): List<PlaceOneLineReviewStatDetailResponse> =
                placeOneLineReviewStatsResult.map {
                    PlaceOneLineReviewStatDetailResponse(
                        contents = it.contents,
                        count = it.count,
                    )
                }
        }
    }
}
