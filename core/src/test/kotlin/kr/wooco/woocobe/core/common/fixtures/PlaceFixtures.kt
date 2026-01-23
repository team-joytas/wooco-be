package kr.wooco.woocobe.core.common.fixtures

import kr.wooco.woocobe.core.place.domain.entity.Place

object PlaceFixtures {
    const val DEFAULT_PLACE_ID: Long = 10L

    fun place(
        id: Long = DEFAULT_PLACE_ID,
        name: String = "우코 카페",
        latitude: Double = 37.0,
        longitude: Double = 127.0,
        address: String = "서울 어딘가",
        kakaoPlaceId: String = "kakao-123",
        averageRating: Double = 4.5,
        reviewCount: Long = 3L,
        phoneNumber: String = "010-0000-0000",
        thumbnailUrl: String = "https://image.wooco.com/thumb.png",
    ): Place =
        Place(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoPlaceId = kakaoPlaceId,
            averageRating = averageRating,
            reviewCount = reviewCount,
            phoneNumber = phoneNumber,
            thumbnailUrl = thumbnailUrl,
        )
}
