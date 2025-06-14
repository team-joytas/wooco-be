package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.domain.command.CreateCourseCommand
import kr.wooco.woocobe.core.course.domain.entity.Course
import kr.wooco.woocobe.core.course.domain.vo.CourseCategory
import kr.wooco.woocobe.core.course.domain.vo.CourseContent
import kr.wooco.woocobe.core.course.domain.vo.CourseRegion
import java.time.LocalDate

fun interface CreateCourseUseCase {
    data class Command(
        val userId: Long,
        val primaryRegion: String,
        val secondaryRegion: String,
        val categories: List<String>,
        val title: String,
        val contents: String,
        val placeIds: List<Long>,
        val visitDate: LocalDate,
    ) {
        fun toCreateCommand(): CreateCourseCommand =
            CreateCourseCommand(
                userId = userId,
                region = CourseRegion(
                    primaryRegion = primaryRegion,
                    secondaryRegion = secondaryRegion,
                ),
                content = CourseContent(
                    title = title,
                    contents = contents,
                ),
                categories = categories.map { CourseCategory(it) },
                visitDate = Course.VisitDate(visitDate),
                placeIds = placeIds,
            )
    }

    fun createCourse(command: Command): Long
}
