package kr.wooco.woocobe.api.region.response

import kr.wooco.woocobe.core.region.application.query.service.dto.PreferenceRegionData

data class PreferenceDetailResponse(
    val id: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun from(preferenceRegionData: PreferenceRegionData): PreferenceDetailResponse =
            PreferenceDetailResponse(
                preferenceRegionData.id,
                preferenceRegionData.primaryRegion,
                preferenceRegionData.secondaryRegion,
            )

        fun listFrom(preferenceRegionData: List<PreferenceRegionData>): List<PreferenceDetailResponse> =
            preferenceRegionData.map(Companion::from)
    }
}
