package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.usecase.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanRegion
import org.springframework.stereotype.Service
import java.time.LocalDate

data class AddPlanInput(
    val userId: Long,
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
    val categories: List<String>,
)

data class AddPlanOutput(
    val planId: Long,
)

@Service
class AddPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<AddPlanInput, AddPlanOutput> {
    @Transactional
    override fun execute(input: AddPlanInput): AddPlanOutput {
        val region = PlanRegion(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
        )

        val plan = planStorageGateway.save(
            Plan.register(
                userId = input.userId,
                title = input.title,
                contents = input.contents,
                region = region,
                visitDate = input.visitDate,
                placeIds = input.placeIds,
                categories = input.categories,
            ),
        )

        return AddPlanOutput(
            planId = plan.id,
        )
    }
}
