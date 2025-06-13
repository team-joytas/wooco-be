package kr.wooco.woocobe.core.coursecomment.application.port.`in`

import kr.wooco.woocobe.core.coursecomment.domain.command.DeleteCommentCommand

fun interface DeleteCourseCommentUseCase {
    data class Command(
        val userId: Long,
        val courseCommentId: Long,
    ) {
        fun toDeleteCommand(): DeleteCommentCommand =
            DeleteCommentCommand(
                userId = userId,
            )
    }

    fun deleteCourseComment(command: Command)
}
