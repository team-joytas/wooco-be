package kr.wooco.woocobe.place.domain.gateway

import kr.wooco.woocobe.place.domain.model.PlaceOneLineReviewStat

interface PlaceOneLineReviewStorageGateway {
    fun getOneLineReviewStats(placeId: Long): List<PlaceOneLineReviewStat>
}
