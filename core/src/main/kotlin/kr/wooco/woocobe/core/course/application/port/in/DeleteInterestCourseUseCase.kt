package kr.wooco.woocobe.core.course.application.port.`in`

fun interface DeleteInterestCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    )

    fun deleteInterestCourse(command: Command)
}
