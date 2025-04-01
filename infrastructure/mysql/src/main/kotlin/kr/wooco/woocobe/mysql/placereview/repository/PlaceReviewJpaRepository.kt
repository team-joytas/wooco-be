package kr.wooco.woocobe.mysql.placereview.repository

import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
import kr.wooco.woocobe.mysql.placereview.entity.PlaceReviewJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PlaceReviewJpaRepository : JpaRepository<PlaceReviewJpaEntity, Long> {
    fun findAllByPlaceIdOrderByCreatedAt(placeId: Long): List<PlaceReviewJpaEntity>

    fun findAllByUserIdOrderByCreatedAt(userId: Long): List<PlaceReviewJpaEntity>

    @Query(
        """
            SELECT COALESCE(AVG(pr.rating), 0.0)
            FROM PlaceReviewJpaEntity pr 
            WHERE pr.placeId = :placeId
        """,
    )
    fun findAverageRatingByPlaceId(placeId: Long): Double

    @Query(
        """
            SELECT new kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats(
            COALESCE(AVG(pr.rating), 0.0), COUNT(*))
            FROM PlaceReviewJpaEntity pr 
            WHERE pr.placeId = :placeId
        """,
    )
    fun findPlaceReviewStatsByPlaceId(placeId: Long): PlaceReviewStats

    fun existsByPlaceIdAndUserId(
        placeId: Long,
        userId: Long,
    ): Boolean

    fun countByUserId(userId: Long): Long
}
