package kr.wooco.woocobe.plan.ui.web.controller.request

import kr.wooco.woocobe.plan.domain.usecase.UpdatePlanInput
import java.time.LocalDate

data class UpdatePlanRequest(
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
    val categories: List<String>,
) {
    fun toCommand(
        userId: Long,
        planId: Long,
    ): UpdatePlanInput =
        UpdatePlanInput(
            userId = userId,
            planId = planId,
            title = title,
            contents = contents,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            visitDate = visitDate,
            placeIds = placeIds,
            categories = categories,
        )
}
