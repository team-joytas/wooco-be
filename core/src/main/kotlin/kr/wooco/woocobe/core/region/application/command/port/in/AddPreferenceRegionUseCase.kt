package kr.wooco.woocobe.core.region.application.command.port.`in`

import kr.wooco.woocobe.core.region.domain.entity.PreferenceRegion

data class AddPreferenceRegionCommand(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    fun toPreferenceRegion(): PreferenceRegion =
        PreferenceRegion(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )
}

fun interface AddPreferenceRegionUseCase {
    fun addPreferenceRegion(command: AddPreferenceRegionCommand): Long
}
