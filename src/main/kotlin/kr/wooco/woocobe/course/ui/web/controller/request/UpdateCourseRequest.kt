package kr.wooco.woocobe.course.ui.web.controller.request

import kr.wooco.woocobe.course.domain.usecase.UpdateCourseInput
import java.time.LocalDate

data class UpdateCourseRequest(
    val title: String,
    val contents: String,
    val categories: List<String>,
    val placeIds: List<Long>,
    val visitDate: LocalDate,
) {
    fun toCommand(
        userId: Long,
        courseId: Long,
    ): UpdateCourseInput =
        UpdateCourseInput(
            userId = userId,
            courseId = courseId,
            title = title,
            contents = contents,
            categories = categories,
            placeIds = placeIds,
            visitDate = visitDate,
        )
}
