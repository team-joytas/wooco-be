package kr.wooco.woocobe.core.placereview.application.port.out

import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview

interface LoadPlaceReviewPersistencePort {
    fun getByPlaceReviewId(placeReviewId: Long): PlaceReview

    fun getAllByPlaceId(placeId: Long): List<PlaceReview>

    fun getAllByUserId(userId: Long): List<PlaceReview>

    fun getAverageRatingByPlaceId(placeId: Long): Double
}
