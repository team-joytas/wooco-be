package kr.wooco.woocobe.core.notification.application.handler

import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.ReadDeviceTokenUseCase
import kr.wooco.woocobe.core.notification.application.port.`in`.SendNotificationUseCase
import kr.wooco.woocobe.core.notification.domain.vo.NotificationType
import kr.wooco.woocobe.core.plan.domain.event.PlanShareRequestEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationEventHandler(
    private val readDeviceTokenUseCase: ReadDeviceTokenUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleCourseCommentCreateEvent(event: CourseCommentCreateEvent) {
        val command = CreateNotificationUseCase.Command(
            userId = event.courseWriterId,
            targetId = event.courseId,
            targetName = event.courseTitle,
            type = NotificationType.COURSE_COMMENT_CREATED.name,
        )
        sendNotification(command)
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handlePlanShareRequestEvent(event: PlanShareRequestEvent) {
        val command = CreateNotificationUseCase.Command(
            userId = event.userId,
            targetId = event.planId,
            targetName = event.planTitle,
            type = NotificationType.PLAN_SHARE_REQUEST.name,
        )
        sendNotification(command)
    }

    private fun sendNotification(command: CreateNotificationUseCase.Command) {
        val notification = createNotificationUseCase.createNotification(command)
        val query = ReadDeviceTokenUseCase.Query(notification.userId)
        val deviceToken = readDeviceTokenUseCase.readDeviceToken(query)
        val request = SendNotificationUseCase.Request(notification, deviceToken.token)
        sendNotificationUseCase.sendNotification(request)
    }
}
