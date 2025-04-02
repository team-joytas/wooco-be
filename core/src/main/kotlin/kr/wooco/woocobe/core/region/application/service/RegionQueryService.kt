package kr.wooco.woocobe.core.region.application.service

import kr.wooco.woocobe.core.region.application.port.`in`.ReadAllPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.ReadPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.port.`in`.result.PreferenceRegionResult
import kr.wooco.woocobe.core.region.application.port.out.PreferenceRegionQueryPort
import kr.wooco.woocobe.core.region.domain.vo.Region
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RegionQueryService(
    private val preferenceRegionQueryPort: PreferenceRegionQueryPort,
) : ReadPreferenceRegionUseCase,
    ReadAllPreferenceRegionUseCase {
    @Transactional(readOnly = true)
    override fun readPreferenceRegion(query: ReadPreferenceRegionUseCase.Query): PreferenceRegionResult {
        val region = Region(primaryRegion = query.primaryRegion, secondaryRegion = query.secondaryRegion)
        val preferenceRegion = preferenceRegionQueryPort.getByUserIdAndRegion(query.userId, region)
        return PreferenceRegionResult.from(preferenceRegion)
    }

    @Transactional(readOnly = true)
    override fun readAllPreferenceRegion(query: ReadAllPreferenceRegionUseCase.Query): List<PreferenceRegionResult> {
        val preferenceRegions = preferenceRegionQueryPort.getAllByUserId(query.userId)
        return preferenceRegions.map { PreferenceRegionResult.from(it) }
    }
}
