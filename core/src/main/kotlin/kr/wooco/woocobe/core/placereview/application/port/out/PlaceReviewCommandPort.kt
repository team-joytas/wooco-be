package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

interface PlaceReviewCommandPort {
    fun savePlaceReview(placeReview: PlaceReview): Long

    fun deletePlaceReviewId(placeReviewId: Long)

    fun saveAllPlaceOneLineReview(placeOneLineReview: List<PlaceOneLineReview>): List<PlaceOneLineReview>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
