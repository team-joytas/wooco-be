package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.PlanDate
import kr.wooco.woocobe.plan.domain.model.PlanRegion
import org.springframework.stereotype.Service
import java.time.LocalDate

data class UpdatePlanInput(
    val userId: Long,
    val planId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
)

@Service
class UpdatePlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<UpdatePlanInput, Unit> {
    @Transactional
    override fun execute(input: UpdatePlanInput) {
        val plan = planStorageGateway.getById(input.planId)
            ?: throw RuntimeException("Not exists plan")

        when {
            plan.isWriter(input.userId).not() -> throw RuntimeException()
        }

        val region = PlanRegion.register(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
        )

        val visitDate = PlanDate.register(input.visitDate)

        plan
            .update(
                region = region,
                visitDate = visitDate,
            ).also(planStorageGateway::save)
    }
}
