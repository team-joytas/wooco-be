package kr.wooco.woocobe.core.course.application.handler

import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.domain.event.CourseLikeCreatedEvent
import kr.wooco.woocobe.core.course.domain.event.CourseLikeDeletedEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentDeletedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

// TODO: AFTER_COMMIT 으로 변경 & 지연 전용 Buffer Layer 가 필요할듯

@Component
internal class CourseProjectionHandler(
    private val courseQueryPort: CourseQueryPort,
) {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleCommentCreatedEvent(event: CourseCommentCreateEvent) {
        courseQueryPort.increaseComments(event.courseId)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleCommentDeletedEvent(event: CourseCommentDeletedEvent) {
        courseQueryPort.decreaseComments(event.courseId)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleLikeCreatedEvent(event: CourseLikeCreatedEvent) {
        courseQueryPort.increaseLikes(event.courseId)
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleLikeDeletedEvent(event: CourseLikeDeletedEvent) {
        courseQueryPort.decreaseLikes(event.courseId)
    }
}
