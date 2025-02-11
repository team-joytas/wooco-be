package kr.wooco.woocobe.course.application.port.`in`

fun interface DeleteCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    )

    fun deleteCourse(command: Command)
}
