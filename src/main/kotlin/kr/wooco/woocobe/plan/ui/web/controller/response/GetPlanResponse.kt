package kr.wooco.woocobe.plan.ui.web.controller.response

import kr.wooco.woocobe.plan.domain.model.PlanPlace
import kr.wooco.woocobe.plan.domain.usecase.GetPlanOutput
import java.time.LocalDate

data class GetPlanResponse(
    val planId: Long,
    val title: String,
    val description: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val places: List<PlanPlace>,
    val categories: List<String>,
) {
    companion object {
        fun from(getPlanOutput: GetPlanOutput) =
            with(getPlanOutput) {
                GetPlanResponse(
                    planId = plan.id,
                    title = plan.title,
                    description = plan.description,
                    primaryRegion = plan.region.primaryRegion,
                    secondaryRegion = plan.region.secondaryRegion,
                    visitDate = plan.visitDate,
                    places = plan.places,
                    categories = plan.categories.map { it.name },
                )
            }
    }
}
