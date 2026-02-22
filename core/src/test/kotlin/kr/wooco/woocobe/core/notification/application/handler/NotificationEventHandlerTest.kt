package kr.wooco.woocobe.core.notification.application.handler

import kr.wooco.woocobe.core.coursecomment.domain.event.CourseCommentCreateEvent
import kr.wooco.woocobe.core.notification.application.port.`in`.CreateNotificationUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class NotificationEventHandlerTest {
    @Mock
    lateinit var createNotificationUseCase: CreateNotificationUseCase

    private lateinit var handler: NotificationEventHandler

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T = Mockito.any<T>()

    @BeforeEach
    fun setUp() {
        handler = NotificationEventHandler(
            createNotificationUseCase = createNotificationUseCase,
        )
    }

    @Test
    @DisplayName("본인 코스에 본인이 댓글 달면 알림을 생성하지 않는다")
    fun skipSelfComment() {
        val event = CourseCommentCreateEvent(
            aggregateId = 1L,
            courseId = 10L,
            courseTitle = "테스트 코스",
            courseWriterId = 100L,
            commentWriterId = 100L,
        )

        handler.handleCourseCommentCreateEvent(event)

        then(createNotificationUseCase).shouldHaveNoInteractions()
    }

    @Test
    @DisplayName("댓글 이벤트 수신 시 알림을 생성한다")
    fun createNotificationOnComment() {
        val event = CourseCommentCreateEvent(
            aggregateId = 1L,
            courseId = 10L,
            courseTitle = "테스트 코스",
            courseWriterId = 100L,
            commentWriterId = 200L,
        )

        given(createNotificationUseCase.createNotification(any())).willReturn(1L)

        handler.handleCourseCommentCreateEvent(event)

        then(createNotificationUseCase).should().createNotification(any())
    }
}
