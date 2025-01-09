package kr.wooco.woocobe.place.infrastructure.storage.repository

import kr.wooco.woocobe.place.infrastructure.storage.entity.PlaceReviewJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PlaceReviewJpaRepository : JpaRepository<PlaceReviewJpaEntity, Long> {
    fun findAllByPlaceIdOrderByCreatedAt(placeId: Long): List<PlaceReviewJpaEntity>

    fun findAllByIdInOrderByCreatedAt(placeReviewIds: List<Long>): List<PlaceReviewJpaEntity>

    fun findAllByUserIdOrderByCreatedAt(userId: Long): List<PlaceReviewJpaEntity>
}
