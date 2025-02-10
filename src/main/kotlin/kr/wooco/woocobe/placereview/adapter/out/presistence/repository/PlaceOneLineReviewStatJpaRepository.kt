package kr.wooco.woocobe.placereview.adapter.out.presistence.repository

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewStatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceOneLineReviewStatJpaRepository : JpaRepository<PlaceOneLineReviewStatJpaEntity, Long> {
    fun findAllByPlaceId(placeId: Long): List<PlaceOneLineReviewStatJpaEntity>

    fun findAllByPlaceIdIn(placeIds: List<Long>): List<PlaceOneLineReviewStatJpaEntity>
}
