package kr.wooco.woocobe.plan.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import org.springframework.stereotype.Service

data class GetAllPlansInput(
    val userId: Long,
)

data class GetAllPlansOutput(
    val plans: List<GetPlanOutput>,
)

@Service
class GetAllPlansUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<GetAllPlansInput, GetAllPlansOutput> {
    override fun execute(input: GetAllPlansInput): GetAllPlansOutput =
        GetAllPlansOutput(
            planStorageGateway.getAllByUserId(input.userId)!!.map { plan ->
                GetPlanOutput(
                    plan.id,
                    plan.writer.id,
                    plan.writer.name,
                    plan.regionInfo.primaryRegion,
                    plan.regionInfo.secondaryRegion,
                    plan.visitDate.toString(),
                )
            },
        )
}
