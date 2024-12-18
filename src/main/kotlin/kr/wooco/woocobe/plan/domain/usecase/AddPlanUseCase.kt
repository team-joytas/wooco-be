package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanDate
import kr.wooco.woocobe.plan.domain.model.PlanRegion
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import org.springframework.stereotype.Service
import java.time.LocalDate

// TODO: 장소 정보 추가
data class AddPlanInput(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
)

@Service
class AddPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
    private val userStorageGateway: UserStorageGateway,
) : UseCase<AddPlanInput, Unit> {
    @Transactional
    override fun execute(input: AddPlanInput) {
        val user = userStorageGateway.getByUserId(input.userId)
            ?: throw RuntimeException()

        val region = PlanRegion.register(
            primaryRegion = input.primaryRegion,
            secondaryRegion = input.secondaryRegion,
        )

        val visitDate = PlanDate.register(input.visitDate)

        Plan
            .register(
                user = user,
                region = region,
                visitDate = visitDate,
            ).also(planStorageGateway::save)
    }
}
