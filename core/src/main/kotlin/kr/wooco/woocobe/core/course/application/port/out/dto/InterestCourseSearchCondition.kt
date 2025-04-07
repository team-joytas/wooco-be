package kr.wooco.woocobe.core.course.application.port.out.dto

data class InterestCourseSearchCondition(
    val targetUserId: Long?,
    val category: String?,
    val primaryRegion: String?,
    val secondaryRegion: String?,
    val limit: Int?,
    val sort: String?,
)
