package kr.wooco.woocobe.region.adapter.`in`.web.response

import kr.wooco.woocobe.region.application.query.service.dto.PreferenceRegionData

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
