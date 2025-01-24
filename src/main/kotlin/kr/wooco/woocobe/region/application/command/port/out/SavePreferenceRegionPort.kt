package kr.wooco.woocobe.region.application.command.port.out

import kr.wooco.woocobe.region.domain.entity.PreferenceRegion

interface SavePreferenceRegionPort {
    fun save(preferenceRegion: PreferenceRegion): PreferenceRegion
}
