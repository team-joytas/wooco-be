package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewStatJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceOneLineReviewStatJpaRepository : JpaRepository<PlaceOneLineReviewStatJpaEntity, Long> {
    fun findAllByPlaceId(placeId: Long): List<PlaceOneLineReviewStatJpaEntity>

    fun findAllByPlaceIdIn(placeIds: List<Long>): List<PlaceOneLineReviewStatJpaEntity>
}
