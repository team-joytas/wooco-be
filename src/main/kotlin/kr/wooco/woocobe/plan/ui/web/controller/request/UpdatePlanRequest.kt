package kr.wooco.woocobe.plan.ui.web.controller.request

import java.time.LocalDate

data class UpdatePlanRequest(
    val title: String,
    val description: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val visitDate: LocalDate,
    val placeIds: List<Long>,
    val categories: List<String>,
)
