package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.PlanRegionInfo
import org.springframework.stereotype.Service
import java.time.LocalDate

// TODO: 장소 정보 추가
data class UpdatePlanInput(
    val userId: Long,
    val planId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: String,
) {
    fun validateAndParseDate(): LocalDate =
        runCatching { LocalDate.parse(visitDate) }
            .getOrElse { throw RuntimeException("Invalid visit date format") }
}

@Service
class UpdatePlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
) : UseCase<UpdatePlanInput, Unit> {
    @Transactional
    override fun execute(input: UpdatePlanInput) {
        val findPlan = planStorageGateway.getById(input.planId)
            ?: throw RuntimeException("Not exists plan")

        findPlan.takeIf { it.isWriter(input.userId) }
            ?: throw RuntimeException("Invalid plan writer")

        val updatedPlan = findPlan.withUpdatedValues(
            PlanRegionInfo.of(input.primaryRegion, input.secondaryRegion),
            input.validateAndParseDate(),
        )
        planStorageGateway.save(updatedPlan)
    }
}
