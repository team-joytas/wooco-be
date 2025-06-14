package kr.wooco.woocobe.core.coursecomment.application.port.`in`

import kr.wooco.woocobe.core.coursecomment.domain.command.CreateCommentCommand
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

fun interface CreateCourseCommentUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
        val contents: String,
    ) {
        fun toCreateCommand(
            courseTitle: String,
            courseWriterId: Long,
        ): CreateCommentCommand =
            CreateCommentCommand(
                userId = userId,
                courseId = courseId,
                contents = CourseComment.Contents(
                    value = contents,
                ),
                // FIXME: 아래 두 필드 준투랑 이야기 후 수정
                courseTitle = courseTitle,
                courseWriterId = courseWriterId,
            )
    }

    fun createCourseComment(command: Command): Long
}
