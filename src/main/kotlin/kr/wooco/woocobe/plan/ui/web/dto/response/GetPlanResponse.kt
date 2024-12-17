package kr.wooco.woocobe.plan.ui.web.dto.response

import kr.wooco.woocobe.plan.domain.usecase.GetPlanOutput

// TODO: 장소 데이터 추가
data class GetPlanResponse(
    val planId: Long,
    val writerId: Long,
    val writerName: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: String,
    // val places: List<Place>
) {
    companion object {
        fun from(plan: GetPlanOutput): GetPlanResponse =
            GetPlanResponse(
                plan.id,
                plan.writerId,
                plan.writerName,
                plan.primaryRegion,
                plan.secondaryRegion,
                plan.visitDate,
            )
    }
}
