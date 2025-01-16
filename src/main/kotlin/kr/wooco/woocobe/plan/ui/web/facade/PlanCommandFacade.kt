package kr.wooco.woocobe.plan.ui.web.facade

import kr.wooco.woocobe.plan.domain.usecase.AddPlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.DeletePlanInput
import kr.wooco.woocobe.plan.domain.usecase.DeletePlanUseCase
import kr.wooco.woocobe.plan.domain.usecase.UpdatePlanUseCase
import kr.wooco.woocobe.plan.ui.web.controller.request.CreatePlanRequest
import kr.wooco.woocobe.plan.ui.web.controller.request.UpdatePlanRequest
import kr.wooco.woocobe.plan.ui.web.controller.response.CreatePlanResponse
import org.springframework.stereotype.Service

@Service
class PlanCommandFacade(
    private val addPlanUseCase: AddPlanUseCase,
    private val updatePlanUseCase: UpdatePlanUseCase,
    private val deletePlanUseCase: DeletePlanUseCase,
) {
    fun createPlan(
        userId: Long,
        request: CreatePlanRequest,
    ): CreatePlanResponse {
        val addPlanResult = addPlanUseCase.execute(request.toCommand(userId))
        return CreatePlanResponse(addPlanResult.planId)
    }

    fun updatePlan(
        userId: Long,
        planId: Long,
        request: UpdatePlanRequest,
    ) = updatePlanUseCase.execute(request.toCommand(userId = userId, planId = planId))

    fun deletePlan(
        userId: Long,
        planId: Long,
    ) = deletePlanUseCase.execute(DeletePlanInput(userId = userId, planId = planId))
}
