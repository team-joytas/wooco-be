package kr.wooco.woocobe.region.application.command.port.out

interface DeletePreferenceRegionPort {
    fun deleteByPreferenceRegionId(preferenceRegionId: Long)
}
