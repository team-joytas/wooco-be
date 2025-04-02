package kr.wooco.woocobe.core.region.application.port.`in`

import kr.wooco.woocobe.core.region.application.port.`in`.result.PreferenceRegionResult

fun interface ReadPreferenceRegionUseCase {
    data class Query(
        val userId: Long,
        val primaryRegion: String,
        val secondaryRegion: String,
    )

    fun readPreferenceRegion(query: Query): PreferenceRegionResult
}
