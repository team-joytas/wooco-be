package kr.wooco.woocobe.core.coursecomment.application.service

import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.application.port.out.query.CourseCommentTarget
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import kr.wooco.woocobe.core.coursecomment.application.port.`in`.CreateCourseCommentUseCase
import kr.wooco.woocobe.core.coursecomment.application.port.out.CourseCommentCommandPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CourseCommentCommandServiceTest {
    @Mock
    lateinit var courseQueryPort: CourseQueryPort

    @Mock
    lateinit var courseCommentCommandPort: CourseCommentCommandPort

    private lateinit var courseCommentCommandService: CourseCommentCommandService

    @BeforeEach
    fun setUp() {
        courseCommentCommandService =
            CourseCommentCommandService(
                courseQueryPort = courseQueryPort,
                courseCommentCommandPort = courseCommentCommandPort,
            )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T = Mockito.any<T>()

    @Test
    @DisplayName("삭제된 코스에는 댓글을 등록할 수 없다")
    fun rejectCommentOnDeletedCourse() {
        given(courseQueryPort.getActiveCommentTargetByCourseId(1L)).willThrow(NotExistsCourseException)

        assertThrows<NotExistsCourseException> {
            courseCommentCommandService.createCourseComment(
                CreateCourseCommentUseCase.Command(
                    userId = 1L,
                    courseId = 1L,
                    contents = "댓글 내용",
                ),
            )
        }
    }

    @Test
    @DisplayName("활성 코스에는 댓글을 등록할 수 있다")
    fun createCommentOnActiveCourse() {
        given(courseQueryPort.getActiveCommentTargetByCourseId(1L))
            .willReturn(CourseCommentTarget(title = "코스 제목", writerId = 2L))
        given(courseCommentCommandPort.saveCourseComment(any())).willReturn(10L)

        val result =
            courseCommentCommandService.createCourseComment(
                CreateCourseCommentUseCase.Command(
                    userId = 1L,
                    courseId = 1L,
                    contents = "댓글 내용",
                ),
            )

        assertThat(result).isEqualTo(10L)
    }
}
