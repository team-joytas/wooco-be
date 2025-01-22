package kr.wooco.woocobe.region.application.command.port.out

import kr.wooco.woocobe.region.domain.entity.PreferenceRegion

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
