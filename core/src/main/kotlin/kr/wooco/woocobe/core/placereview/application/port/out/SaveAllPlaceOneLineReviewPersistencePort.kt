package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceOneLineReview

interface SaveAllPlaceOneLineReviewPersistencePort {
    fun saveAllPlaceOneLineReview(placeOneLineReview: List<PlaceOneLineReview>): List<PlaceOneLineReview>
}
