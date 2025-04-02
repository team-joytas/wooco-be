package kr.wooco.woocobe.core.plan.application.service

import kr.wooco.woocobe.core.plan.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.UpdatePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.out.PlanCommandPort
import kr.wooco.woocobe.core.plan.application.port.out.PlanQueryPort
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.vo.PlanRegion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlanCommandService(
    private val planQueryPort: PlanQueryPort,
    private val planCommandPort: PlanCommandPort,
) : CreatePlanUseCase,
    UpdatePlanUseCase,
    DeletePlanUseCase {
    @Transactional
    override fun createPlan(command: CreatePlanUseCase.Command): Long {
        val planRegion = PlanRegion(
            primaryRegion = command.primaryRegion,
            secondaryRegion = command.secondaryRegion,
        )
        val plan = Plan.create(
            userId = command.userId,
            title = command.title,
            contents = command.contents,
            region = planRegion,
            visitDate = command.visitDate,
            placeIds = command.placeIds,
        )
        return planCommandPort.savePlan(plan).id
    }

    @Transactional
    override fun updatePlan(command: UpdatePlanUseCase.Command) {
        val plan = planQueryPort.getByPlanId(command.planId)
        val region = PlanRegion(
            primaryRegion = command.primaryRegion,
            secondaryRegion = command.secondaryRegion,
        )
        val updatedPlan = plan.update(
            userId = command.userId,
            title = command.title,
            contents = command.contents,
            region = region,
            visitDate = command.visitDate,
            placeIds = command.placeIds,
        )
        planCommandPort.savePlan(updatedPlan)
    }

    @Transactional
    override fun deletePlan(command: DeletePlanUseCase.Command) {
        val plan = planQueryPort.getByPlanId(command.planId)
        val deletedPlan = plan.delete(command.userId)
        planCommandPort.savePlan(deletedPlan)
        planCommandPort.deleteAllPlanPlaceByPlanId(plan.id)
    }
}
