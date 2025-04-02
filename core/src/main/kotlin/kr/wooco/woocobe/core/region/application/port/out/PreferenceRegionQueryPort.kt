package kr.wooco.woocobe.core.region.application.port.out

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion
import kr.wooco.woocobe.core.region.domain.vo.Region

interface PreferenceRegionQueryPort {
    fun getByUserIdAndRegion(
        userId: Long,
        region: Region,
    ): PreferenceRegion

    fun getByUserIdAndPreferenceRegionId(
        userId: Long,
        preferenceRegionId: Long,
    ): PreferenceRegion

    fun getAllByUserId(userId: Long): List<PreferenceRegion>

    fun existsByUserIdAndRegion(
        userId: Long,
        region: Region,
    ): Boolean
}
