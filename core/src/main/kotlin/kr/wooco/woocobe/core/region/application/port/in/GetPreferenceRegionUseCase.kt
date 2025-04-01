package kr.wooco.woocobe.core.region.application.port.`in`

import kr.wooco.woocobe.core.region.application.port.out.dto.PreferenceRegionData

data class GetPreferenceRegionQuery(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
)

fun interface GetPreferenceRegionUseCase {
    fun getPreferenceRegion(query: GetPreferenceRegionQuery): PreferenceRegionData
}
