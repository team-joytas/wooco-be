package kr.wooco.woocobe.core.course.domain.command

data class CreateLikeCourseCommand(
    val userId: Long,
    val courseId: Long,
)
