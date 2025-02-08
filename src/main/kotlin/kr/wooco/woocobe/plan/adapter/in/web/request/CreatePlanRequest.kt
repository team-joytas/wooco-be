package kr.wooco.woocobe.plan.adapter.`in`.web.request

import kr.wooco.woocobe.plan.application.port.`in`.CreatePlanUseCase
import java.time.LocalDate

data class CreatePlanRequest(
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
    val categories: List<String>,
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
            categories = categories,
        )
}
