package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.domain.command.DeleteCourseCommand

fun interface DeleteCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    ) {
        fun toDeleteCommand(): DeleteCourseCommand =
            DeleteCourseCommand(
                userId = userId,
            )
    }

    fun deleteCourse(command: Command)
}
