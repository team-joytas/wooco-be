package kr.wooco.woocobe.place.domain.gateway

import kr.wooco.woocobe.place.domain.model.Place
import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

interface PlaceStorageGateway {
    fun save(place: Place): Place

    fun getByPlaceId(placeId: Long): Place

    fun getByKakaoMapPlaceIdOrNull(kakaoMapPlaceId: String): Place?

    fun getAllByPlaceIds(placeIds: List<Long>): List<Place>

    fun getOneLineReviewStats(placeId: Long): List<PlaceOneLineReviewStat>
}
