package kr.wooco.woocobe.core.calendar.schedule.application.service

import kr.wooco.woocobe.core.calendar.group.application.port.out.GroupCommandPort
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.`in`.UpdatePlanInfoUseCase
import kr.wooco.woocobe.core.calendar.schedule.application.port.out.SchedulePlanCommandPort
import kr.wooco.woocobe.core.calendar.schedule.domain.entity.Plan
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SchedulePlanCommandService(
    private val schedulePlanCommandPort: SchedulePlanCommandPort,
    private val groupCommandPort: GroupCommandPort,
) : CreatePlanUseCase,
    UpdatePlanInfoUseCase,
    DeletePlanUseCase {
    override fun createPlan(command: CreatePlanUseCase.Command): Long {
        val group = groupCommandPort.getById(command.groupId)
        group.requireMember(command.userId)

        val plan = Plan.create(command.toCreateCommand()) { schedulePlanCommandPort.save(it) }
        return plan.id
    }

    @Transactional
    override fun updatePlanInfo(command: UpdatePlanInfoUseCase.Command): Long {
        val plan = schedulePlanCommandPort.getById(command.planId)
        val group = groupCommandPort.getById(plan.groupId)
        group.requireMember(command.userId)

        val updated = plan.updateInfo(command.toUpdateCommand())
        schedulePlanCommandPort.save(updated)
        return updated.id
    }

    @Transactional
    override fun deletePlan(command: DeletePlanUseCase.Command): Long {
        val plan = schedulePlanCommandPort.getById(command.planId)
        val group = groupCommandPort.getById(plan.groupId)
        group.requireMember(command.userId)

        val deleted = plan.delete()
        schedulePlanCommandPort.save(deleted)
        return deleted.id
    }
}
