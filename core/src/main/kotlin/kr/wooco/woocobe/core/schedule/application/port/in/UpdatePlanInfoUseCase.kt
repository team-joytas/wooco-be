package kr.wooco.woocobe.core.schedule.application.port.`in`

import kr.wooco.woocobe.core.schedule.domain.command.UpdatePlanInfoCommand
import kr.wooco.woocobe.core.schedule.domain.entity.Plan
import java.time.LocalDate

interface UpdatePlanInfoUseCase {
    data class Command(
        val userId: Long,
        val planId: Long,
        val title: String,
        val visitDate: LocalDate,
        val placeIds: List<Long>,
    ) {
        fun toUpdateCommand(): UpdatePlanInfoCommand =
            UpdatePlanInfoCommand(
                userId = userId,
                planId = planId,
                title = Plan.Title(title),
                visitDate = Plan.VisitDate(visitDate),
                placeIds = placeIds,
            )
    }

    fun updatePlanInfo(command: UpdatePlanInfoUseCase.Command): Long
}
