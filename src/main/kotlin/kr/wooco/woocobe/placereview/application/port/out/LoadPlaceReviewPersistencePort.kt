package kr.wooco.woocobe.placereview.application.port.out

import kr.wooco.woocobe.placereview.domain.entity.PlaceReview

interface LoadPlaceReviewPersistencePort {
    fun getByPlaceReviewId(placeReviewId: Long): PlaceReview

    fun getAllByPlaceId(placeId: Long): List<PlaceReview>

    fun getAllByUserId(userId: Long): List<PlaceReview>

    fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceReview>
}
