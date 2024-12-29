package kr.wooco.woocobe.place.infrastructure.storage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceOneLineReviewJpaRepository : JpaRepository<PlaceOneLineReviewEntity, Long> {
    fun findAllByPlaceReviewIdOrderByCreatedAt(placeReviewId: Long): List<PlaceOneLineReviewEntity>

    fun findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewId: List<Long>): List<PlaceOneLineReviewEntity>

    @Query(
        """
            select r.content as content, count(r.content) as count
            from PlaceOneLineReviewEntity r
            where r.placeId = :placeId
            group by r.content
            order by count desc
        """,
    )
    fun findPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<Map<String, Long>>
}
