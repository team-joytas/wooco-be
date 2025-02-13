package kr.wooco.woocobe.core.course.application.port.`in`

fun interface CreateInterestCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    )

    fun createInterestCourse(command: Command)
}
