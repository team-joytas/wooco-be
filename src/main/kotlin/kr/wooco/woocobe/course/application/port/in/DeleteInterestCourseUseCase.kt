package kr.wooco.woocobe.course.application.port.`in`

fun interface DeleteInterestCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    )

    fun deleteInterestCourse(command: Command)
}
