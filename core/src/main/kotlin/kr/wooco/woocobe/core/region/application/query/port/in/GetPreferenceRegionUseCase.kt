package kr.wooco.woocobe.core.region.application.query.port.`in`

import kr.wooco.woocobe.core.region.application.query.service.dto.PreferenceRegionData

data class GetPreferenceRegionQuery(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
)

fun interface GetPreferenceRegionUseCase {
    fun getPreferenceRegion(query: GetPreferenceRegionQuery): PreferenceRegionData
}
