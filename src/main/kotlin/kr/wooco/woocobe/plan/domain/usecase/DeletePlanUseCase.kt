package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import org.springframework.stereotype.Service

data class DeletePlanInput(
    val userId: Long,
    val planId: Long,
)

@Service
class DeletePlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<DeletePlanInput, Unit> {
    @Transactional
    override fun execute(input: DeletePlanInput) {
        val findPlan = planStorageGateway.getById(input.planId)
            ?: throw RuntimeException("Not exists plan")

        findPlan.takeIf { it.isWriter(input.userId) }
            ?: throw RuntimeException("Invalid plan writer")

        planStorageGateway.deleteById(input.planId)
    }
}
