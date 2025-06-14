package kr.wooco.woocobe.core.coursecomment.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

// FIXME: 준투랑 이야기 후 과거형으로 수정 & courseWriterId, courseTitle 해결

data class CourseCommentCreateEvent(
    override val aggregateId: Long,
    val courseId: Long,
    val courseTitle: String,
    val courseWriterId: Long,
    val commentWriterId: Long,
) : DomainEvent() {
    companion object {
        fun of(
            courseTitle: String,
            courseWriterId: Long,
            courseComment: CourseComment,
        ): CourseCommentCreateEvent =
            CourseCommentCreateEvent(
                aggregateId = courseComment.id,
                courseId = courseComment.courseId,
                commentWriterId = courseComment.userId,
                // 아래 2필드 논의 필요
                courseTitle = courseTitle,
                courseWriterId = courseWriterId,
            )
    }
}
