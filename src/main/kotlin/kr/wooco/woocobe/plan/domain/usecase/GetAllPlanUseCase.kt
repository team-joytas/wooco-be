package kr.wooco.woocobe.plan.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import org.springframework.stereotype.Service

data class GetAllPlanInput(
    val userId: Long,
)

data class GetAllPlanOutput(
    val plans: List<Plan>,
)

@Service
class GetAllPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<GetAllPlanInput, GetAllPlanOutput> {
    override fun execute(input: GetAllPlanInput): GetAllPlanOutput {
        val plans = planStorageGateway.getAllByUserId(input.userId)

        return GetAllPlanOutput(
            plans = plans,
        )
    }
}
