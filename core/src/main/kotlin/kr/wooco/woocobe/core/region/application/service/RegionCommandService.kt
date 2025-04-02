package kr.wooco.woocobe.core.region.application.service

import kr.wooco.woocobe.core.region.application.port.`in`.CreatePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.DeletePreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.out.PreferenceRegionCommandPort
import kr.wooco.woocobe.core.region.application.port.out.PreferenceRegionQueryPort
import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion
import kr.wooco.woocobe.core.region.domain.exception.AlreadyPreferenceRegionException
import kr.wooco.woocobe.core.region.domain.vo.Region
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RegionCommandService(
    private val preferenceRegionQueryPort: PreferenceRegionQueryPort,
    private val preferenceRegionCommandPort: PreferenceRegionCommandPort,
) : CreatePreferenceRegionUseCase,
    DeletePreferenceRegionUseCase {
    @Transactional
    override fun createPreferenceRegion(command: CreatePreferenceRegionUseCase.Command): Long {
        val region = Region(primaryRegion = command.primaryRegion, command.secondaryRegion)
        val isExists = preferenceRegionQueryPort.existsByUserIdAndRegion(
            userId = command.userId,
            region = region,
        )
        if (isExists) throw AlreadyPreferenceRegionException
        val preferenceRegion = PreferenceRegion.create(userId = command.userId, region = region)
        return preferenceRegionCommandPort.savePreferenceRegion(preferenceRegion).id
    }

    @Transactional
    override fun deletePreferenceRegion(command: DeletePreferenceRegionUseCase.Command) {
        val preferenceRegion = preferenceRegionQueryPort.getByUserIdAndPreferenceRegionId(
            userId = command.userId,
            preferenceRegionId = command.preferenceRegionId,
        )
        preferenceRegionCommandPort.deletePreferenceRegion(preferenceRegion)
    }
}
