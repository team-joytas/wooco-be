package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

interface SavePlaceReviewPersistencePort {
    fun savePlaceReview(placeReview: PlaceReview): PlaceReview
}
