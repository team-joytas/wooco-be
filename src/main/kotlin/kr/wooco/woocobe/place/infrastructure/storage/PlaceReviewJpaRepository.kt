package kr.wooco.woocobe.place.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface PlaceReviewJpaRepository : JpaRepository<PlaceReviewEntity, Long> {
    fun findAllByPlaceIdOrderByCreatedAt(placeId: Long): List<PlaceReviewEntity>

    fun findAllByIdInOrderByCreatedAt(placeReviewIds: List<Long>): List<PlaceReviewEntity>

    fun findAllByUserIdOrderByCreatedAt(userId: Long): List<PlaceReviewEntity>
}
