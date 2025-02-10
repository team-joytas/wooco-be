package kr.wooco.woocobe.placereview.application.port.out

import kr.wooco.woocobe.placereview.domain.entity.PlaceOneLineReviewStat

interface LoadPlaceOneLineReviewStatPersistencePort {
    fun getAllStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat>

    fun getAllStatsByPlaceIds(placeIds: List<Long>): List<PlaceOneLineReviewStat>
}
