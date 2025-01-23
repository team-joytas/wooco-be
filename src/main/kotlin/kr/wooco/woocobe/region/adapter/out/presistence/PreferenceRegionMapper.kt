package kr.wooco.woocobe.region.adapter.out.presistence

import kr.wooco.woocobe.region.adapter.out.presistence.entity.PreferenceRegionJpaEntity
import kr.wooco.woocobe.region.domain.entity.PreferenceRegion
import org.springframework.stereotype.Component

@Component
class PreferenceRegionMapper {
    fun toDomain(preferenceRegionJpaEntity: PreferenceRegionJpaEntity): PreferenceRegion =
        PreferenceRegion(
            id = preferenceRegionJpaEntity.id,
            userId = preferenceRegionJpaEntity.userId,
            region = PreferenceRegion.Region(
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
