package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.CreateInterestCourseUseCase
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeCommandPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.course.domain.exception.NotExistsCourseException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CourseLikeCommandServiceTest {
    @Mock
    lateinit var courseQueryPort: CourseQueryPort

    @Mock
    lateinit var courseLikeCommandPort: CourseLikeCommandPort

    private lateinit var courseLikeCommandService: CourseLikeCommandService

    @BeforeEach
    fun setUp() {
        courseLikeCommandService =
            CourseLikeCommandService(
                courseQueryPort = courseQueryPort,
                courseLikeCommandPort = courseLikeCommandPort,
            )
    }

    @Test
    @DisplayName("삭제된 코스에는 좋아요를 등록할 수 없다")
    fun rejectLikeOnDeletedCourse() {
        given(courseQueryPort.existsActiveByCourseId(1L)).willReturn(false)

        assertThrows<NotExistsCourseException> {
            courseLikeCommandService.createInterestCourse(
                CreateInterestCourseUseCase.Command(
                    userId = 1L,
                    courseId = 1L,
                ),
            )
        }

        verifyNoInteractions(courseLikeCommandPort)
    }
}
