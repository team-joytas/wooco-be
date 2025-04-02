package kr.wooco.woocobe.core.region.application.port.`in`

import kr.wooco.woocobe.core.region.application.port.`in`.result.PreferenceRegionResult

fun interface ReadAllPreferenceRegionUseCase {
    data class Query(
        val userId: Long,
    )

    fun readAllPreferenceRegion(query: Query): List<PreferenceRegionResult>
}
