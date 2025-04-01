package kr.wooco.woocobe.core.region.application.port.`in`

import kr.wooco.woocobe.core.region.application.port.out.dto.PreferenceRegionData

data class GetAllPreferenceRegionQuery(
    val userId: Long,
)

fun interface GetAllPreferenceRegionUseCase {
    fun getAllPreferenceRegion(query: GetAllPreferenceRegionQuery): List<PreferenceRegionData>
}
