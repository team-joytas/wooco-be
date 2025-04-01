package kr.wooco.woocobe.core.region.application.port.out

import kr.wooco.woocobe.core.region.application.port.out.dto.PreferenceRegionData

interface LoadPreferenceRegionDataPort {
    fun getByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): PreferenceRegionData

    fun getAllByUserId(userId: Long): List<PreferenceRegionData>
}
