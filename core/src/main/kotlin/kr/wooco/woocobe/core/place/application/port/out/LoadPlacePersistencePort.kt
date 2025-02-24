package kr.wooco.woocobe.core.place.application.port.out

import kr.wooco.woocobe.core.place.domain.entity.Place

interface LoadPlacePersistencePort {
    fun getByPlaceId(placeId: Long): Place

    fun getOrNullByKakaoMapPlaceId(kakaoMapPlaceId: String): Place?

    fun getAllByPlaceIds(placeIds: List<Long>): List<Place>
}
