package kr.wooco.woocobe.mysql.place.repository

import kr.wooco.woocobe.mysql.place.entity.PlaceJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface PlaceJpaRepository : JpaRepository<PlaceJpaEntity, Long> {
    fun findByKakaoPlaceId(kakaoMapPlaceId: String): PlaceJpaEntity?

    fun findAllByIdIn(placeIds: List<Long>): List<PlaceJpaEntity>

    @Query(
        """
        SELECT p.id FROM PlaceJpaEntity p
        WHERE (p.thumbnailFetchedAt < :threshold AND p.thumbnailRefreshFailCount < :maxRetry)
        OR ((p.thumbnailUrl IS NULL OR p.thumbnailUrl = '') AND p.thumbnailRefreshFailCount < :maxRetry)
        ORDER BY p.thumbnailFetchedAt ASC NULLS FIRST
        """,
    )
    fun findPlaceIdsNeedingThumbnailRefresh(
        @Param("threshold") threshold: LocalDateTime,
        @Param("maxRetry") maxRetry: Int,
        pageable: Pageable,
    ): List<Long>

    @Modifying(clearAutomatically = true)
    @Query(
        """
        UPDATE PlaceJpaEntity p
        SET p.thumbnailFetchedAt = :now, p.thumbnailRefreshFailCount = 0
        WHERE p.id = :placeId
        """,
    )
    fun markThumbnailRefreshed(
        @Param("placeId") placeId: Long,
        @Param("now") now: LocalDateTime,
    )

    @Modifying(clearAutomatically = true)
    @Query(
        """
        UPDATE PlaceJpaEntity p
        SET p.thumbnailRefreshFailCount = p.thumbnailRefreshFailCount + 1
        WHERE p.id = :placeId
        """,
    )
    fun markThumbnailRefreshFailed(
        @Param("placeId") placeId: Long,
    )
}
