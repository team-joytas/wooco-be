package kr.wooco.woocobe.placereview.adapter.out.presistence.repository

import kr.wooco.woocobe.placereview.adapter.out.presistence.entity.PlaceOneLineReviewJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceOneLineReviewJpaRepository : JpaRepository<PlaceOneLineReviewJpaEntity, Long> {
    fun findAllByPlaceReviewIdOrderByCreatedAt(placeReviewId: Long): List<PlaceOneLineReviewJpaEntity>

    fun findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewIds: List<Long>): List<PlaceOneLineReviewJpaEntity>

    @Query(
        """
            select r.contents as contents, count(r.contents) as count
            from PlaceOneLineReviewJpaEntity r
            where r.placeId = :placeId
            group by r.contents
            order by count desc
        """,
    )
    fun findPlaceOneLineReviewStatsByPlaceId(placeId: Long): List<Map<String, Long>>

    fun findAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReviewJpaEntity>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
