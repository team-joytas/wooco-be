package kr.wooco.woocobe.mysql.region

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion
import kr.wooco.woocobe.core.region.domain.vo.Region
import kr.wooco.woocobe.mysql.region.entity.PreferenceRegionJpaEntity
import org.springframework.stereotype.Component

@Component
internal class PreferenceRegionMapper {
    fun toDomain(preferenceRegionJpaEntity: PreferenceRegionJpaEntity): PreferenceRegion =
        PreferenceRegion(
            id = preferenceRegionJpaEntity.id,
            userId = preferenceRegionJpaEntity.userId,
            region = Region(
                primaryRegion = preferenceRegionJpaEntity.primaryRegion,
                secondaryRegion = preferenceRegionJpaEntity.secondaryRegion,
            ),
        )

    fun toEntity(preferenceRegion: PreferenceRegion): PreferenceRegionJpaEntity =
        PreferenceRegionJpaEntity(
            id = preferenceRegion.id,
            userId = preferenceRegion.userId,
            primaryRegion = preferenceRegion.region.primaryRegion,
            secondaryRegion = preferenceRegion.region.secondaryRegion,
        )
}
