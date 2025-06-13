package kr.wooco.woocobe.core.course.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.course.domain.entity.CourseLike

data class CourseLikeDeletedEvent(
    override val aggregateId: Long,
    val courseId: Long,
) : DomainEvent() {
    companion object {
        fun of(courseLike: CourseLike): CourseLikeDeletedEvent =
            CourseLikeDeletedEvent(
                aggregateId = courseLike.id,
                courseId = courseLike.courseId,
            )
    }
}
