package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.mysql.placereview.entity.PlaceOneLineReviewJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceOneLineReviewJpaRepository : JpaRepository<PlaceOneLineReviewJpaEntity, Long> {
    fun findAllByPlaceReviewIdIn(placeReviewIds: List<Long>): List<PlaceOneLineReviewJpaEntity>

    @Query(
        """
            SELECT new kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat(
                polr.contents, COUNT(polr.contents)
            )
            FROM PlaceOneLineReviewJpaEntity polr
            JOIN PlaceReviewJpaEntity pr 
              ON pr.id = polr.placeReviewId
            WHERE pr.status = 'ACTIVE'
              AND pr.placeId = :placeId
            GROUP BY polr.contents
            ORDER BY COUNT(polr.contents) DESC
        """,
    )
    fun findPlaceOneLineReviewStatsByPlaceId(
        placeId: Long,
        pageable: Pageable,
    ): List<PlaceOneLineReviewStat>

    fun findAllByPlaceReviewId(placeReviewId: Long): List<PlaceOneLineReviewJpaEntity>

    fun deleteAllByPlaceReviewId(placeReviewId: Long)
}
