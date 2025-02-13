package kr.wooco.woocobe.api.region.request

import kr.wooco.woocobe.core.region.application.command.port.`in`.AddPreferenceRegionCommand

data class CreatePreferenceRegionRequest(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    fun toCommand(userId: Long): AddPreferenceRegionCommand =
        AddPreferenceRegionCommand(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )
}
