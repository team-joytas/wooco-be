package kr.wooco.woocobe.core.coursecomment.application.port.`in`

import kr.wooco.woocobe.core.coursecomment.domain.command.UpdateCommentContentsCommand
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

fun interface UpdateCourseCommentUseCase {
    data class Command(
        val courseCommentId: Long,
        val userId: Long,
        val contents: String,
    ) {
        fun toUpdateContentsCommand(): UpdateCommentContentsCommand =
            UpdateCommentContentsCommand(
                userId = userId,
                contents = CourseComment.Contents(
                    value = contents,
                ),
            )
    }

    fun updateCourseComment(command: Command)
}
