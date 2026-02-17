package kr.wooco.woocobe.core.course.domain.event

import kr.wooco.woocobe.core.common.domain.event.DomainEvent
import kr.wooco.woocobe.core.course.domain.entity.Course

data class CourseCreatedEvent(
    override val aggregateId: Long,
) : DomainEvent() {
    companion object {
        fun of(course: Course): CourseCreatedEvent =
            CourseCreatedEvent(
                aggregateId = course.id,
            )
    }
}
