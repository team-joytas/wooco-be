package kr.wooco.woocobe.core.region.application.command.port.out

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion

interface SavePreferenceRegionPort {
    fun save(preferenceRegion: PreferenceRegion): PreferenceRegion
}
