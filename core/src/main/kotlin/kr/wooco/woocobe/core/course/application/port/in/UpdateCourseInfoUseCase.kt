package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.domain.command.UpdateCourseInfoCommand
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent
import java.time.LocalDate

fun interface UpdateCourseInfoUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
        val title: String,
        val contents: String,
        val placeIds: List<Long>,
        val visitDate: LocalDate,
        val categories: List<String>,
    ) {
        fun toUpdateInfoCommand(): UpdateCourseInfoCommand =
            UpdateCourseInfoCommand(
                userId = userId,
                content = CourseContent(
                    title = title,
                    contents = contents,
                ),
                categories = categories.map { CourseCategory(it) },
                visitDate = Course.VisitDate(visitDate),
                placeIds = placeIds,
            )
    }

    fun updateCourseInfo(command: Command)
}
