package kr.wooco.woocobe.region.adapter.out.presistence

import kr.wooco.woocobe.region.adapter.out.presistence.repository.PreferenceRegionJpaRepository
import kr.wooco.woocobe.region.application.command.port.out.DeletePreferenceRegionPort
import kr.wooco.woocobe.region.application.command.port.out.LoadPreferenceRegionPort
import kr.wooco.woocobe.region.application.command.port.out.SavePreferenceRegionPort
import kr.wooco.woocobe.region.application.query.port.out.LoadPreferenceRegionDataPort
import kr.wooco.woocobe.region.application.query.service.dto.PreferenceRegionData
import kr.wooco.woocobe.region.domain.entity.PreferenceRegion
import kr.wooco.woocobe.region.domain.exception.NotExistsPreferenceRegionException
import org.springframework.stereotype.Component

@Component
internal class PreferenceRegionPersistenceAdapter(
    private val preferenceRegionMapper: PreferenceRegionMapper,
    private val preferenceRegionJpaRepository: PreferenceRegionJpaRepository,
) : SavePreferenceRegionPort,
    LoadPreferenceRegionPort,
    DeletePreferenceRegionPort,
    LoadPreferenceRegionDataPort {
    override fun save(preferenceRegion: PreferenceRegion): PreferenceRegion {
        val preferenceRegionEntity =
            preferenceRegionJpaRepository.save(preferenceRegionMapper.toEntity(preferenceRegion))
        return preferenceRegionMapper.toDomain(preferenceRegionEntity)
    }

    override fun existsByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): Boolean =
        preferenceRegionJpaRepository.existsByUserIdAndRegion(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )

    override fun getByUserIdAndPreferenceRegionId(
        userId: Long,
        preferenceRegionId: Long,
    ): PreferenceRegion {
        val preferenceRegionEntity =
            preferenceRegionJpaRepository.findByUserIdAndPreferenceRegionId(userId, preferenceRegionId)
                ?: throw NotExistsPreferenceRegionException
        return preferenceRegionMapper.toDomain(preferenceRegionEntity)
    }

    override fun deleteByPreferenceRegionId(preferenceRegionId: Long) {
        preferenceRegionJpaRepository.deleteById(preferenceRegionId)
    }

    override fun getByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): PreferenceRegionData =
        preferenceRegionJpaRepository.findByUserIdAndPrimaryRegionAndSecondaryRegion(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        ) ?: throw NotExistsPreferenceRegionException

    override fun getAllByUserId(userId: Long): List<PreferenceRegionData> = preferenceRegionJpaRepository.findAllByUserId(userId)
}
