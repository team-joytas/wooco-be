package kr.wooco.woocobe.plan.ui.web.dto.response

import kr.wooco.woocobe.plan.domain.usecase.GetPlanOutput

// TODO: 장소 데이터 추가
data class GetPlanResponse(
    val planId: Long,
    val userId: Long,
    val userName: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: String,
    // val places: List<Place>
) {
    companion object {
        fun from(getPlanOutput: GetPlanOutput) =
            with(getPlanOutput.plan) {
                GetPlanResponse(
                    planId = id,
                    userId = user.id,
                    userName = user.name,
                    primaryRegion = region.primaryRegion,
                    secondaryRegion = region.secondaryRegion,
                    visitDate = visitDate.toString(),
                )
            }
    }
}
