package kr.wooco.woocobe.core.course.application.port.`in`

import java.time.LocalDate

fun interface UpdateCourseUseCase {
    data class Command(
        val userId: Long,
        val courseId: Long,
        val title: String,
        val contents: String,
        val categories: List<String>,
        val placeIds: List<Long>,
        val visitDate: LocalDate,
    )

    fun updateCourse(command: Command)
}
