package kr.wooco.woocobe.plan.domain.usecase

import jakarta.transaction.Transactional
import kr.wooco.woocobe.common.domain.UseCase
import kr.wooco.woocobe.plan.domain.gateway.PlanStorageGateway
import kr.wooco.woocobe.plan.domain.model.Plan
import kr.wooco.woocobe.plan.domain.model.PlanRegionInfo
import kr.wooco.woocobe.user.domain.gateway.UserStorageGateway
import org.springframework.stereotype.Service
import java.time.LocalDate

// TODO: 장소 정보 추가
data class AddPlanInput(
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: String,
) {
    fun validateAndParseDate(): LocalDate =
        runCatching { LocalDate.parse(visitDate) }
            .getOrElse { throw RuntimeException("Invalid visit date format") }
}

@Service
class AddPlanUseCase(
    private val planStorageGateway: PlanStorageGateway,
    private val userStorageGateway: UserStorageGateway,
) : UseCase<AddPlanInput, Unit> {
    @Transactional
    override fun execute(input: AddPlanInput) {
        val findUser = userStorageGateway.getByUserId(input.userId)!!
        val plan = Plan.register(
            user = findUser,
            regionInfo = PlanRegionInfo.of(input.primaryRegion, input.secondaryRegion),
            visitDate = input.validateAndParseDate(),
        )
        planStorageGateway.save(plan)
    }
}
