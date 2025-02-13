package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewImageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceReviewImageJpaRepository : JpaRepository<PlaceReviewImageJpaEntity, Long> {
    fun findAllByPlaceReviewId(placeReviewId: Long): List<PlaceReviewImageJpaEntity>

    fun findAllByPlaceReviewIdIn(placeReviewIds: List<Long>): List<PlaceReviewImageJpaEntity>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
