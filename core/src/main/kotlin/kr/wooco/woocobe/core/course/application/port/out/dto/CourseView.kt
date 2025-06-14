package kr.wooco.woocobe.core.course.application.port.out.dto

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 코스 Read Model
 *
 * @author JiHongKim98
 */
data class CourseView(
    val id: Long,
    val userId: Long,
    val title: String,
    val contents: String,
    val primaryRegion: String,
    val secondaryRegion: String,
    val categories: List<String>,
    val visitDate: LocalDate,
    val comments: Long,
    val likes: Long,
    val createdAt: LocalDateTime,
    val coursePlaces: List<CoursePlaceView>,
) {
    data class CoursePlaceView(
        val order: Int,
        val placeId: Long,
    )
}
