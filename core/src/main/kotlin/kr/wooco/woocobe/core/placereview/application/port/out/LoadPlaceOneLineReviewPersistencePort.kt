package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview

interface LoadPlaceOneLineReviewPersistencePort {
    fun getAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReview>

    fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceOneLineReview>

    fun getAllPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<PlaceOneLineReviewStat>
}
