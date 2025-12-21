package kr.wooco.woocobe.api.schedule.request

import kr.wooco.woocobe.core.schedule.application.port.`in`.UpdatePlanInfoUseCase
import java.time.LocalDate

data class UpdatePlanInfoRequest(
    val planId: Long,
    val title: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
) {
    fun toCommand(userId: Long): UpdatePlanInfoUseCase.Command =
        UpdatePlanInfoUseCase.Command(
            userId = userId,
            planId = planId,
            title = title,
            visitDate = visitDate,
            placeIds = placeIds,
        )
}
