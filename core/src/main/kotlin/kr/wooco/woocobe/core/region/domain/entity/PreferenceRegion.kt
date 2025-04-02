package kr.wooco.woocobe.core.region.domain.entity

import kr.wooco.woocobe.core.region.domain.vo.Region

data class PreferenceRegion(
    val id: Long,
    val userId: Long,
    val region: Region,
) {
    companion object {
        fun create(
            userId: Long,
            region: Region,
        ): PreferenceRegion =
            PreferenceRegion(
                id = 0L,
                userId = userId,
                region = region,
            )
    }
}
