package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceOneLineReviewJpaRepository : JpaRepository<PlaceOneLineReviewJpaEntity, Long> {
    fun findAllByPlaceReviewIdInOrderByCreatedAt(placeReviewIds: List<Long>): List<PlaceOneLineReviewJpaEntity>

    @Query(
        """
    SELECT new kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat(
        polr.content, COUNT(polr.content)
    )
    FROM PlaceOneLineReviewJpaEntity polr
    WHERE polr.placeId = :placeId
    GROUP BY polr.content
    ORDER BY COUNT(polr.content) DESC
""",
    )
    fun findPlaceOneLineReviewStatsByPlaceId(
        placeId: Long,
        pageable: Pageable,
    ): List<PlaceOneLineReviewStat>

    fun findAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReviewJpaEntity>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
