package kr.wooco.woocobe.core.notification.application.handler

import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import kr.wooco.woocobe.core.plan.domain.event.PlanShareRequestEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationEventHandler(
    private val createNotificationUseCase: CreateNotificationUseCase,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleCourseCommentCreateEvent(event: CourseCommentCreateEvent) {
        if (event.courseWriterId == event.commentWriterId) return

        createNotificationUseCase.createNotification(
            CreateNotificationUseCase.Command(
                userId = event.courseWriterId,
                targetId = event.courseId,
                targetName = event.courseTitle,
                type = NotificationType.COURSE_COMMENT_CREATED.name,
            ),
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePlanShareRequestEvent(event: PlanShareRequestEvent) {
        createNotificationUseCase.createNotification(
            CreateNotificationUseCase.Command(
                userId = event.userId,
                targetId = event.planId,
                targetName = event.planTitle,
                type = NotificationType.PLAN_SHARE_REQUEST.name,
            ),
        )
    }
}
