package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.application.port.`in`.results.CourseResult

fun interface ReadAllInterestCourseUseCase {
    data class Query(
        val targetUserId: Long,
        val userId: Long?,
        val category: String? = null,
        val primaryRegion: String? = null,
        val secondaryRegion: String? = null,
        val limit: Int? = null,
        val sort: String? = null,
    )

    fun readAllUserInterestCourse(query: Query): List<CourseResult>
}
