package kr.wooco.woocobe.core.coursecomment.application.port.`in`

fun interface CreateCourseCommentUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
        val contents: String,
    )

    fun createCourseComment(command: Command): Long
}
