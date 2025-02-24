package kr.wooco.woocobe.core.course.application.port.`in`

import java.time.LocalDate

fun interface CreateCourseUseCase {
    data class Command(
        val userId: Long,
        val primaryRegion: String,
        val secondaryRegion: String,
        val categories: List<String>,
        val title: String,
        val contents: String,
        val placeIds: List<Long>,
        val visitDate: LocalDate,
    )

    fun createCourse(command: Command): Long
}
