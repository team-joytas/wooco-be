package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceReviewJpaRepository : JpaRepository<PlaceReviewJpaEntity, Long> {
    fun findAllByPlaceIdOrderByCreatedAt(placeId: Long): List<PlaceReviewJpaEntity>

    fun findAllByIdInOrderByCreatedAt(placeReviewIds: List<Long>): List<PlaceReviewJpaEntity>

    fun findAllByUserIdOrderByCreatedAt(userId: Long): List<PlaceReviewJpaEntity>
}
