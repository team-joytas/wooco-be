package kr.wooco.woocobe.api.region.response

import kr.wooco.woocobe.core.region.application.port.`in`.result.PreferenceRegionResult

data class PreferenceRegionResponse(
    val id: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun from(preferenceRegionResult: PreferenceRegionResult): PreferenceRegionResponse =
            PreferenceRegionResponse(
                preferenceRegionResult.id,
                preferenceRegionResult.primaryRegion,
                preferenceRegionResult.secondaryRegion,
            )

        fun listFrom(preferenceRegionResult: List<PreferenceRegionResult>): List<PreferenceRegionResponse> =
            preferenceRegionResult.map(Companion::from)
    }
}
