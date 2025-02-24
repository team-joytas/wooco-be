package kr.wooco.woocobe.api.course.request

import kr.wooco.woocobe.core.course.application.port.`in`.CreateCourseUseCase
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
    fun toCommand(userId: Long): CreateCourseUseCase.Command =
        CreateCourseUseCase.Command(
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
