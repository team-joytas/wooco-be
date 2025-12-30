package kr.wooco.woocobe.core.calendar.schedule.application.service

import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupCommandPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.UpdatePlanInfoUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanCommandPort
import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan
import kr.wooco.woocobe.core.calendar.schedule.domain.exception.PlanAccessDeniedException
import org.springframework.stereotype.Service

@Service
class SchedulePlanCommandService(
    private val schedulePlanCommandPort: SchedulePlanCommandPort,
    private val groupCommandPort: GroupCommandPort,
) : CreatePlanUseCase,
    UpdatePlanInfoUseCase,
    DeletePlanUseCase {
    override fun createPlan(command: CreatePlanUseCase.Command): Long {
        validateGroupMember(groupId = command.groupId, userId = command.userId)
        val plan = Plan.Companion.create(command.toCreateCommand()) { schedulePlanCommandPort.save(it) }
        return plan.id
    }

    override fun updatePlanInfo(command: UpdatePlanInfoUseCase.Command): Long {
        val plan = schedulePlanCommandPort.getById(command.planId)
        validateGroupMember(groupId = plan.groupId, userId = command.userId)

        val updated = plan.updateInfo(command.toUpdateCommand())
        schedulePlanCommandPort.save(updated)
        return updated.id
    }

    override fun deletePlan(command: DeletePlanUseCase.Command): Long {
        val plan = schedulePlanCommandPort.getById(command.planId)
        validateGroupMember(groupId = plan.groupId, userId = command.userId)

        val deleted = plan.delete()
        schedulePlanCommandPort.save(deleted)
        return deleted.id
    }

    private fun validateGroupMember(groupId: Long, userId: Long) {
        val group = groupCommandPort.getById(groupId)
        if (!group.isMember(userId)) {
            throw PlanAccessDeniedException
        }
    }
}
