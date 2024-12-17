package kr.wooco.woocobe.plan.domain.usecase

import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import org.springframework.stereotype.Service

data class GetPlanInput(
    val planId: Long,
)

// TODO: 장소 정보 추가
data class GetPlanOutput(
    val id: Long,
    val writerId: Long,
    val writerName: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: String,
)

@Service
class GetPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<GetPlanInput, GetPlanOutput> {
    override fun execute(input: GetPlanInput): GetPlanOutput =
        planStorageGateway.getById(input.planId)?.let { plan ->
            GetPlanOutput(
                id = plan.id,
                writerId = plan.writer.id,
                writerName = plan.writer.name,
                primaryRegion = plan.regionInfo.primaryRegion,
                secondaryRegion = plan.regionInfo.secondaryRegion,
                visitDate = plan.visitDate.toString(),
            )
        } ?: throw RuntimeException("Not exists plan")
}
