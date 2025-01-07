package kr.wooco.woocobe.plan.ui.web.controller.response

import kr.wooco.woocobe.plan.domain.model.PlanPlace
import kr.wooco.woocobe.plan.domain.usecase.GetAllPlanOutput
import java.time.LocalDate

data class GetAllPlanResponse(
    val plans: List<SimplePlanResponse>,
) {
    companion object {
        fun from(getAllPlanOutput: GetAllPlanOutput): GetAllPlanResponse =
            GetAllPlanResponse(
                getAllPlanOutput.plans.map { plan ->
                    SimplePlanResponse(
                        planId = plan.id,
                        title = plan.title,
                        primaryRegion = plan.region.primaryRegion,
                        secondaryRegion = plan.region.secondaryRegion,
                        visitDate = plan.visitDate,
                        places = plan.places,
                    )
                },
            )
    }
}

data class SimplePlanResponse(
    val planId: Long,
    val title: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val places: List<PlanPlace>,
)
