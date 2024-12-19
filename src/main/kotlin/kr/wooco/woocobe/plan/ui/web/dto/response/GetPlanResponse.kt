package kr.wooco.woocobe.plan.ui.web.dto.response

import kr.wooco.woocobe.plan.domain.usecase.GetPlanOutput
import java.time.LocalDate

// TODO: 장소 데이터 추가
data class GetPlanResponse(
    val planId: Long,
    val userId: Long,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    // val places: List<Place>
) {
    companion object {
        fun from(getPlanOutput: GetPlanOutput) =
            with(getPlanOutput) {
                GetPlanResponse(
                    planId = plan.id,
                    userId = plan.userId,
                    primaryRegion = plan.region.primaryRegion,
                    secondaryRegion = plan.region.secondaryRegion,
                    visitDate = plan.visitDate.date,
                )
            }
    }
}
