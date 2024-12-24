package kr.wooco.woocobe.place.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository

interface PlaceOneLineReviewJpaRepository : JpaRepository<PlaceOneLineReviewEntity, Long> {
    fun findAllByPlaceReviewIdOrderByCreatedAt(placeReviewId: Long): List<PlaceOneLineReviewEntity>

    fun findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewId: List<Long>): List<PlaceOneLineReviewEntity>
}
