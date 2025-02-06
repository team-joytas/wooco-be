package kr.wooco.woocobe.coursecomment.application.port.`in`

import kr.wooco.woocobe.coursecomment.application.port.`in`.results.CourseCommentResult

fun interface ReadAllCourseCommentUseCase {
    data class Query(
        val courseId: Long,
    )

    fun readAllCourseComment(query: Query): List<CourseCommentResult>
}
