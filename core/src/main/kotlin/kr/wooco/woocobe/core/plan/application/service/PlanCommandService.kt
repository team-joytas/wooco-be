package kr.wooco.woocobe.core.plan.application.service

import kr.wooco.woocobe.core.plan.application.port.`in`.CreatePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.DeletePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.SharePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.`in`.UpdatePlanUseCase
import kr.wooco.woocobe.core.plan.application.port.out.DeletePlanPersistencePort
import kr.wooco.woocobe.core.plan.application.port.out.LoadPlanPersistencePort
import kr.wooco.woocobe.core.plan.application.port.out.SavePlanPersistencePort
import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.vo.PlanRegion
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PlanCommandService(
    private val loadPlanPersistencePort: LoadPlanPersistencePort,
    private val savePlanPersistencePort: SavePlanPersistencePort,
    private val deletePlanPersistencePort: DeletePlanPersistencePort,
) : CreatePlanUseCase,
    UpdatePlanUseCase,
    DeletePlanUseCase,
    SharePlanUseCase {
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
        return savePlanPersistencePort.savePlan(plan).id
    }

    @Transactional
    override fun updatePlan(command: UpdatePlanUseCase.Command) {
        val plan = loadPlanPersistencePort.getByPlanId(command.planId)
        val region = PlanRegion(
            primaryRegion = command.primaryRegion,
            secondaryRegion = command.secondaryRegion,
        )
        plan.update(
            userId = command.userId,
            title = command.title,
            contents = command.contents,
            region = region,
            visitDate = command.visitDate,
            placeIds = command.placeIds,
        )
        savePlanPersistencePort.savePlan(plan)
    }

    @Transactional
    override fun deletePlan(command: DeletePlanUseCase.Command) {
        val plan = loadPlanPersistencePort.getByPlanId(command.planId)
        plan.validateWriter(command.userId)
        deletePlanPersistencePort.deleteByPlanId(plan.id)
    }

    @Transactional
    override fun sharePlan(command: SharePlanUseCase.Command) {
        val plan = loadPlanPersistencePort.getByPlanId(command.planId)
        plan.validateWriter(command.userId)
        plan.share()
        savePlanPersistencePort.savePlan(plan)
    }
}
