package kr.wooco.woocobe.core.region.application.port.out

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion

interface LoadPreferenceRegionPort {
    fun getByUserIdAndPreferenceRegionId(
        userId: Long,
        preferenceRegionId: Long,
    ): PreferenceRegion

    fun existsByUserIdAndRegion(
        userId: Long,
        primaryRegion: String,
        secondaryRegion: String,
    ): Boolean
}
