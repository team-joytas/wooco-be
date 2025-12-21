package kr.wooco.woocobe.core.schedule.application.port.`in`

import kr.wooco.woocobe.core.schedule.domain.command.CreatePlanCommand
import kr.wooco.woocobe.core.schedule.domain.entity.Plan
import java.time.LocalDate

interface CreatePlanUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
        val title: String,
        val visitDate: LocalDate,
        val placeIds: List<Long>,
    ) {
        fun toCreateCommand(): CreatePlanCommand =
            CreatePlanCommand(
                userId = userId,
                groupId = groupId,
                title = Plan.Title(title),
                visitDate = Plan.VisitDate(visitDate),
                placeIds = placeIds,
            )
    }

    fun createPlan(command: CreatePlanUseCase.Command): Long
}
