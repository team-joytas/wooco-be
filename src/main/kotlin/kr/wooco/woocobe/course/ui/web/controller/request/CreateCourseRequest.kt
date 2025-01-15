package kr.wooco.woocobe.course.ui.web.controller.request

import kr.wooco.woocobe.course.domain.usecase.AddCourseUseCaseInput

data class CreateCourseRequest(
    val primaryRegion: String,
    val secondaryRegion: String,
    val category: List<String>,
    val name: String,
    val contents: String,
    val placeIds: List<Long>,
) {
    fun toCommand(userId: Long): AddCourseUseCaseInput =
        AddCourseUseCaseInput(
            userId = userId,
            primaryRegion = primaryRegion,
            secondaryRegion = secondaryRegion,
            category = category,
            name = name,
            contents = contents,
            placeIds = placeIds,
        )
}
