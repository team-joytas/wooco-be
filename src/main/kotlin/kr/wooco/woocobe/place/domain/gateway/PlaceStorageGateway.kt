package kr.wooco.woocobe.place.domain.gateway

import kr.wooco.woocobe.place.domain.model.Place

interface PlaceStorageGateway {
    fun save(place: Place): Place

    fun getByPlaceId(placeId: Long): Place?

    fun existsByKakaoMapPlaceId(kakaoMapPlaceId: String): Boolean

    fun getByKakaoMapPlaceId(kakaoMapPlaceId: String): Place
}
