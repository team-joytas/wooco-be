package kr.wooco.woocobe.region.application.query.port.`in`

import kr.wooco.woocobe.region.application.query.service.dto.PreferenceRegionData

data class GetPreferenceRegionQuery(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
)

fun interface GetPreferenceRegionUseCase {
    fun getPreferenceRegion(query: GetPreferenceRegionQuery): PreferenceRegionData
}
