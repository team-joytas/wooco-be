package kr.wooco.woocobe.mysql.region

import kr.wooco.woocobe.core.region.application.port.out.PreferenceRegionCommandPort
import kr.wooco.woocobe.core.region.application.port.out.PreferenceRegionQueryPort
import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion
import kr.wooco.woocobe.core.region.domain.exception.NotExistsPreferenceRegionException
import kr.wooco.woocobe.core.region.domain.vo.Region
import kr.wooco.woocobe.mysql.region.repository.PreferenceRegionJpaRepository
import org.springframework.stereotype.Component

@Component
internal class PreferenceRegionPersistenceAdapter(
    private val preferenceRegionJpaRepository: PreferenceRegionJpaRepository,
) : PreferenceRegionQueryPort,
    PreferenceRegionCommandPort {
    override fun getByUserIdAndRegion(
        userId: Long,
        region: Region,
    ): PreferenceRegion {
        val preferenceRegionJpaEntity = preferenceRegionJpaRepository.findByUserIdAndRegion(
            userId = userId,
            primaryRegion = region.primaryRegion,
            secondaryRegion = region.secondaryRegion,
        ) ?: throw NotExistsPreferenceRegionException
        return PreferenceRegionPersistenceMapper.toDomainEntity(preferenceRegionJpaEntity)
    }

    override fun getByUserIdAndPreferenceRegionId(
        userId: Long,
        preferenceRegionId: Long,
    ): PreferenceRegion {
        val preferenceRegionJpaEntity = preferenceRegionJpaRepository.findByUserIdAndPreferenceRegionId(
            userId = userId,
            preferenceRegionId = preferenceRegionId,
        ) ?: throw NotExistsPreferenceRegionException
        return PreferenceRegionPersistenceMapper.toDomainEntity(preferenceRegionJpaEntity)
    }

    override fun getAllByUserId(userId: Long): List<PreferenceRegion> {
        val preferenceRegionJpaEntities = preferenceRegionJpaRepository.findAllByUserId(userId)
        return preferenceRegionJpaEntities.map { PreferenceRegionPersistenceMapper.toDomainEntity(it) }
    }

    override fun existsByUserIdAndRegion(
        userId: Long,
        region: Region,
    ): Boolean =
        preferenceRegionJpaRepository.existsByUserIdAndRegion(
            userId = userId,
            primaryRegion = region.primaryRegion,
            secondaryRegion = region.secondaryRegion,
        )

    override fun savePreferenceRegion(preferenceRegion: PreferenceRegion): PreferenceRegion {
        val preferenceRegionEntity =
            preferenceRegionJpaRepository.save(PreferenceRegionPersistenceMapper.toJpaEntity(preferenceRegion))
        return PreferenceRegionPersistenceMapper.toDomainEntity(preferenceRegionEntity)
    }

    override fun deletePreferenceRegion(preferenceRegion: PreferenceRegion) {
        preferenceRegionJpaRepository.deleteById(preferenceRegion.id)
    }
}
