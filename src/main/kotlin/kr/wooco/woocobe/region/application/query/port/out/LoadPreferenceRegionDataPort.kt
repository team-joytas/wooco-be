package kr.wooco.woocobe.region.application.query.port.out

import kr.wooco.woocobe.region.application.query.service.dto.PreferenceRegionData

interface LoadPreferenceRegionDataPort {
    fun getByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): PreferenceRegionData

    fun getAllByUserId(userId: Long): List<PreferenceRegionData>
}
