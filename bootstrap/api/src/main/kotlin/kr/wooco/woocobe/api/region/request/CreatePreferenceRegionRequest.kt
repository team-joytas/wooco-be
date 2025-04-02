package kr.wooco.woocobe.api.region.request

import kr.wooco.woocobe.core.region.application.port.`in`.CreatePreferenceRegionUseCase

data class CreatePreferenceRegionRequest(
    val primaryRegion: String,
    val secondaryRegion: String,
) {
    fun toCommand(userId: Long): CreatePreferenceRegionUseCase.Command =
        CreatePreferenceRegionUseCase.Command(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
        )
}
