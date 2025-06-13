package kr.wooco.woocobe.core.coursecomment.application.port.out.dto

import java.time.LocalDateTime

data class CourseCommentView(
    val id: Long,
    val userId: Long,
    val courseId: Long,
    val contents: String,
    val createdAt: LocalDateTime,
)
