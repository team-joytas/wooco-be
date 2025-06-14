package kr.wooco.woocobe.core.course.application.port.`in`

import kr.wooco.woocobe.core.course.domain.command.CreateLikeCourseCommand

fun interface CreateInterestCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
    ) {
        fun toCreateCommand(): CreateLikeCourseCommand =
            CreateLikeCourseCommand(
                userId = userId,
                courseId = courseId,
            )
    }

    fun createInterestCourse(command: Command)
}
