package kr.wooco.woocobe.placereview.application.port.out

import kr.wooco.woocobe.placereview.domain.entity.PlaceReview

interface SavePlaceReviewPersistencePort {
    fun savePlaceReview(placeReview: PlaceReview): PlaceReview
}
