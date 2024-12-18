package kr.wooco.woocobe.plan.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import org.springframework.stereotype.Service

data class GetPlanInput(
    val planId: Long,
)

// TODO: 장소 정보 추가
data class GetPlanOutput(
    val plan: Plan,
)

@Service
class GetPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<GetPlanInput, GetPlanOutput> {
    override fun execute(input: GetPlanInput): GetPlanOutput {
        val plan = planStorageGateway.getById(input.planId)
            ?: throw RuntimeException()

        return GetPlanOutput(plan)
    }
}
