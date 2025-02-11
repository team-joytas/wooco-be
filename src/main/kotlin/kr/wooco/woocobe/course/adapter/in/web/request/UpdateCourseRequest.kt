package kr.wooco.woocobe.course.adapter.`in`.web.request

import kr.wooco.woocobe.course.application.port.`in`.UpdateCourseUseCase
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
    ): UpdateCourseUseCase.Command =
        UpdateCourseUseCase.Command(
            userId = userId,
            courseId = courseId,
            title = title,
            contents = contents,
            categories = categories,
            placeIds = placeIds,
            visitDate = visitDate,
        )
}
