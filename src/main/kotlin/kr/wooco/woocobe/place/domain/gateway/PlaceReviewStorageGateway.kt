package kr.wooco.woocobe.place.domain.gateway

import kr.wooco.woocobe.place.domain.model.PlaceReview

interface PlaceReviewStorageGateway {
    fun save(placeReview: PlaceReview): PlaceReview

    fun getByPlaceReviewId(placeReviewId: Long): PlaceReview

    fun getAllByPlaceId(placeId: Long): List<PlaceReview>

    fun getAllByPlaceReviewIds(placeReviewIds: List<Long>): List<PlaceReview>

    fun getAllByUserId(userId: Long): List<PlaceReview>

    fun deleteByPlaceReviewId(placeReviewId: Long)
}
