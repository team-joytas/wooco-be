package kr.wooco.woocobe.coursecomment.application.port.`in`

fun interface UpdateCourseCommentUseCase {
    data class Command(
        val courseCommentId: Long,
        val userId: Long,
        val contents: String,
    )

    fun updateCourseComment(command: Command)
}
