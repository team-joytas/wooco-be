package kr.wooco.woocobe.core.region.application.port.`in`.result

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion

data class PreferenceRegionResult(
    val id: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    companion object {
        fun from(preferenceRegion: PreferenceRegion) =
            PreferenceRegionResult(
                id = preferenceRegion.id,
                primaryRegion = preferenceRegion.region.primaryRegion,
                secondaryRegion = preferenceRegion.region.secondaryRegion,
            )
    }
}
