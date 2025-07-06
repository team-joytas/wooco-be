package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

interface PlaceReviewCommandPort {
    fun savePlaceReview(placeReview: PlaceReview): Long
}
