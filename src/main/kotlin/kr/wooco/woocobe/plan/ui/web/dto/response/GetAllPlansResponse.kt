package kr.wooco.woocobe.plan.ui.web.dto.response

import kr.wooco.woocobe.plan.domain.usecase.GetAllPlansOutput
import kr.wooco.woocobe.plan.domain.usecase.GetPlanOutput

data class GetAllPlansResponse(
    val plans: List<GetPlanResponse>,
) {
    companion object {
        fun from(allPlans: GetAllPlansOutput): GetAllPlansResponse =
            GetAllPlansResponse(
                allPlans.plans.map { plan: GetPlanOutput ->
                    GetPlanResponse(
                        plan.id,
                        plan.writerId,
                        plan.writerName,
                        plan.primaryRegion,
                        plan.secondaryRegion,
                        plan.visitDate,
                    )
                },
            )
    }
}
