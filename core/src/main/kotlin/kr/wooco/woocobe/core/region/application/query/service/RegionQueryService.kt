package kr.wooco.woocobe.core.region.application.query.service

import kr.wooco.woocobe.core.region.application.query.port.`in`.GetAllPreferenceRegionQuery
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetAllPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetPreferenceRegionQuery
import kr.wooco.woocobe.core.region.application.query.port.`in`.GetPreferenceRegionUseCase
import kr.wooco.woocobe.core.region.application.query.port.out.LoadPreferenceRegionDataPort
import kr.wooco.woocobe.core.region.application.query.service.dto.PreferenceRegionData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class RegionQueryService(
    private val loadPreferenceRegionDataPort: LoadPreferenceRegionDataPort,
) : GetPreferenceRegionUseCase,
    GetAllPreferenceRegionUseCase {
    @Transactional(readOnly = true)
    override fun getPreferenceRegion(query: GetPreferenceRegionQuery): PreferenceRegionData =
        loadPreferenceRegionDataPort.getByUserIdAndRegion(
            userId = query.userId,
            primaryRegion = query.primaryRegion,
            secondaryRegion = query.secondaryRegion,
        )

    @Transactional(readOnly = true)
    override fun getAllPreferenceRegion(query: GetAllPreferenceRegionQuery): List<PreferenceRegionData> =
        loadPreferenceRegionDataPort.getAllByUserId(query.userId)
}
