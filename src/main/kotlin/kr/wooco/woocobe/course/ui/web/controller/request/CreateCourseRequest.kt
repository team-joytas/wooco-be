package kr.wooco.woocobe.course.ui.web.controller.request

import kr.wooco.woocobe.course.domain.usecase.AddCourseUseCaseInput
import java.time.LocalDate

data class CreateCourseRequest(
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val title: String,
    val contents: String,
    val placeIds: List<Long>,
    val visitDate: LocalDate,
) {
    fun toCommand(userId: Long): AddCourseUseCaseInput =
        AddCourseUseCaseInput(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            categories = categories,
            title = title,
            contents = contents,
            placeIds = placeIds,
            visitDate = visitDate,
        )
}
