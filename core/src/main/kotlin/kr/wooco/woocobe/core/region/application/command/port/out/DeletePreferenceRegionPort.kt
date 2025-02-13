package kr.wooco.woocobe.core.region.application.command.port.out

interface DeletePreferenceRegionPort {
    fun deleteByPreferenceRegionId(preferenceRegionId: Long)
}
