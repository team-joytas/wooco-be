package kr.wooco.woocobe.core.region.application.port.out

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion

interface PreferenceRegionCommandPort {
    fun savePreferenceRegion(preferenceRegion: PreferenceRegion): PreferenceRegion

    fun deletePreferenceRegion(preferenceRegion: PreferenceRegion)
}
