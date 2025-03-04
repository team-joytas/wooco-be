package kr.wooco.woocobe.core.coursecomment.application.port.`in`

fun interface DeleteCourseCommentUseCase {
    data class Command(
        val userId: Long,
        val courseCommentId: Long,
    )

    fun deleteCourseComment(command: Command)
}
