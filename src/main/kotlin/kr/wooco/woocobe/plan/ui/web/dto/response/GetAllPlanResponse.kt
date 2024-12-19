package kr.wooco.woocobe.plan.ui.web.dto.response

import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanOutput

data class GetAllPlanResponse(
    val plans: List<GetPlanResponse>,
) {
    companion object {
        fun from(getAllPlanOutput: GetAllPlanOutput): GetAllPlanResponse =
            GetAllPlanResponse(
                getAllPlanOutput.plans.map { plan ->
                    GetPlanResponse(
                        planId = plan.id,
                        userId = plan.userId,
                        primaryRegion = plan.region.primaryRegion,
                        secondaryRegion = plan.region.secondaryRegion,
                        visitDate = plan.visitDate.date,
                    )
                },
            )
    }
}
