package kr.wooco.woocobe.core.place.application.port.out

import kr.wooco.woocobe.core.place.domain.entity.Place

interface PlaceQueryPort {
    fun getByPlaceId(placeId: Long): Place

    fun getOrNullByKakaoPlaceId(kakaoPlaceId: String): Place?

    fun getAllByPlaceIds(placeIds: List<Long>): List<Place>
}
