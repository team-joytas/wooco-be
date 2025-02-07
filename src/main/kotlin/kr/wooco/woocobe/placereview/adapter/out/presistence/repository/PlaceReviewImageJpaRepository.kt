package kr.wooco.woocobe.placereview.adapter.out.presistence.repository

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceReviewImageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceReviewImageJpaRepository : JpaRepository<PlaceReviewImageJpaEntity, Long> {
    fun findAllByPlaceReviewId(placeReviewId: Long): List<PlaceReviewImageJpaEntity>

    fun findAllByPlaceReviewIdIn(placeReviewIds: List<Long>): List<PlaceReviewImageJpaEntity>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
