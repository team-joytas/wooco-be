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
    val description: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
    val categories: List<String>,
)

@Service
class AddPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<AddPlanInput, Unit> {
    @Transactional
    override fun execute(input: AddPlanInput) {
        val region = PlanRegion(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
        )

        val plan = Plan.register(
            userId = input.userId,
            title = input.title,
            description = input.description,
            region = region,
            visitDate = input.visitDate,
            placeIds = input.placeIds,
            categories = input.categories,
        )
        planStorageGateway.save(plan)
    }
}
