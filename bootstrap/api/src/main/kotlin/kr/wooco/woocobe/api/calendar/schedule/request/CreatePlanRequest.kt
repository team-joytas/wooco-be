package kr.wooco.woocobe.api.calendar.schedule.request

import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.CreatePlanUseCase
import java.time.LocalDate

data class CreatePlanRequest(
    val groupId: Long,
    val title: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
) {
    fun toCommand(userId: Long): CreatePlanUseCase.Command =
        CreatePlanUseCase.Command(
            userId = userId,
            groupId = groupId,
            title = title,
            visitDate = visitDate,
            placeIds = placeIds,
        )
}
