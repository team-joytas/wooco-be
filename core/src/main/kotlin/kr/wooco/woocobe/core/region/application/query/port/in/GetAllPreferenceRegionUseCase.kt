package kr.wooco.woocobe.core.region.application.query.port.`in`

import kr.wooco.woocobe.core.region.application.query.service.dto.PreferenceRegionData

data class GetAllPreferenceRegionQuery(
    val userId: Long,
)

fun interface GetAllPreferenceRegionUseCase {
    fun getAllPreferenceRegion(query: GetAllPreferenceRegionQuery): List<PreferenceRegionData>
}
