package kr.wooco.woocobe.core.coursecomment.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.coursecomment.domain.entity.CourseComment

data class CourseCommentDeletedEvent(
    override val aggregateId: Long,
    val courseId: Long,
) : DomainEvent() {
    companion object {
        fun from(courseComment: CourseComment): CourseCommentDeletedEvent =
            CourseCommentDeletedEvent(
                aggregateId = courseComment.id,
                courseId = courseComment.courseId,
            )
    }
}
