package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.application.port.`in`.results.CourseResult

fun interface ReadCourseUseCase {
    data class Query(
        val userId: Long?,
        val courseId: Long,
    )

    fun readCourse(query: Query): CourseResult
}
