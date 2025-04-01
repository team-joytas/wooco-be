package kr.wooco.woocobe.core.region.application.port.out

interface DeletePreferenceRegionPort {
    fun deleteByPreferenceRegionId(preferenceRegionId: Long)
}
