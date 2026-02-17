package kr.wooco.woocobe.core.course.application.projector

import kr.wooco.woocobe.core.course.application.port.out.CourseMetaProjectionPort
import kr.wooco.woocobe.core.course.application.readmodel.CourseMetaProjection
import kr.wooco.woocobe.core.course.domain.event.CourseCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.CourseLikeCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.CourseLikeDeletedEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentDeletedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

// TODO: AFTER_COMMIT 으로 변경 & 지연 전용 Buffer Layer 가 필요할듯

@Component
internal class CourseMetaProjector(
    private val courseMetaProjectionPort: CourseMetaProjectionPort,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleCourseCreatedEvent(event: CourseCreatedEvent) {
        val createdAt = courseMetaProjectionPort.getCreatedAtByCourseId(event.aggregateId)
        courseMetaProjectionPort.save(CourseMetaProjection.initialize(event.aggregateId, createdAt))
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleCommentCreatedEvent(event: CourseCommentCreateEvent) {
        val courseMetaProjection = courseMetaProjectionPort.getByCourseId(event.courseId)
        courseMetaProjectionPort.save(courseMetaProjection.increaseComments())
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleCommentDeletedEvent(event: CourseCommentDeletedEvent) {
        val courseMetaProjection = courseMetaProjectionPort.getByCourseId(event.courseId)
        courseMetaProjectionPort.save(courseMetaProjection.decreaseComments())
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleLikeCreatedEvent(event: CourseLikeCreatedEvent) {
        val courseMetaProjection = courseMetaProjectionPort.getByCourseId(event.courseId)
        courseMetaProjectionPort.save(courseMetaProjection.increaseLikes())
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleLikeDeletedEvent(event: CourseLikeDeletedEvent) {
        val courseMetaProjection = courseMetaProjectionPort.getByCourseId(event.courseId)
        courseMetaProjectionPort.save(courseMetaProjection.decreaseLikes())
    }
}
