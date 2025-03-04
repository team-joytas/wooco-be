package kr.wooco.woocobe.api.plan.request

import kr.wooco.woocobe.core.plan.application.port.`in`.CreatePlanUseCase
import java.time.LocalDate

data class CreatePlanRequest(
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
) {
    fun toCommand(userId: Long): CreatePlanUseCase.Command =
        CreatePlanUseCase.Command(
            userId = userId,
            title = title,
            contents = contents,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            visitDate = visitDate,
            placeIds = placeIds,
        )
}
