package kr.wooco.woocobe.core.course.application.service.dto

data class CourseSearchCondition(
    val writerId: Long?,
    val category: String?,
    val primaryRegion: String?,
    val secondaryRegion: String?,
    val limit: Int?,
    val sort: String?,
)
