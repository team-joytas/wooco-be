package kr.wooco.woocobe.coursecomment.application.port.`in`

import kr.wooco.woocobe.coursecomment.domain.entity.CourseComment

fun interface CreateCourseCommentUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
        val contents: String,
    ) {
        fun toCourseComment(): CourseComment =
            CourseComment(
                userId = userId,
                courseId = courseId,
                contents = contents,
            )
    }

    fun createCourseComment(command: Command): Long
}
