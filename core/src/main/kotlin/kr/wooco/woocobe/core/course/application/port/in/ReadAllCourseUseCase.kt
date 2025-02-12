package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.application.port.`in`.results.CourseResult

fun interface ReadAllCourseUseCase {
    data class Query(
        val userId: Long?,
        val writerId: Long? = null,
        val category: String? = null,
        val primaryRegion: String? = null,
        val secondaryRegion: String? = null,
        val limit: Int? = null,
        val sort: String? = null,
    )

    fun readAllCourse(query: Query): List<CourseResult>
}
