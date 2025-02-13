package kr.wooco.woocobe.mysql.region.repository

import kr.wooco.woocobe.core.region.application.query.service.dto.PreferenceRegionData
import kr.wooco.woocobe.mysql.region.entity.PreferenceRegionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PreferenceRegionJpaRepository : JpaRepository<PreferenceRegionJpaEntity, Long> {
    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT 1 FROM PreferenceRegionJpaEntity pr
                WHERE pr.userId = :userId
                    AND pr.primaryRegion = :primaryRegion
                    AND pr.secondaryRegion = :secondaryRegion
            ) THEN true ELSE false END
        """,
    )
    fun existsByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): Boolean

    @Query(
        """
            SELECT pr FROM PreferenceRegionJpaEntity pr
            WHERE pr.userId = :userId 
                AND pr.id = :preferenceRegionId
        """,
    )
    fun findByUserIdAndPreferenceRegionId(
        userId: Long,
        preferenceRegionId: Long,
    ): PreferenceRegionJpaEntity?

    fun findByUserIdAndPrimaryRegionAndSecondaryRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): PreferenceRegionData?

    fun findAllByUserId(userId: Long): List<PreferenceRegionData>
}
