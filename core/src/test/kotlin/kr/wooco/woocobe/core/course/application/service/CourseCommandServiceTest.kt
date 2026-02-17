package kr.wooco.woocobe.core.course.application.service

import kr.wooco.woocobe.core.course.application.port.`in`.CreateCourseUseCase
import kr.wooco.woocobe.core.course.application.port.out.CourseCommandPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class CourseCommandServiceTest {
    @Mock
    lateinit var courseCommandPort: CourseCommandPort

    private lateinit var courseCommandService: CourseCommandService

    @BeforeEach
    fun setUp() {
        courseCommandService =
            CourseCommandService(
                courseCommandPort = courseCommandPort,
            )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T = Mockito.any<T>()

    @Test
    @DisplayName("코스 생성 시 저장된 course id 를 반환한다")
    fun returnCreatedCourseId() {
        given(courseCommandPort.saveCourse(any())).willReturn(10L)

        val result =
            courseCommandService.createCourse(
                CreateCourseUseCase.Command(
                    userId = 1L,
                    primaryRegion = "서울",
                    secondaryRegion = "강남",
                    categories = listOf("ACTIVITY"),
                    title = "주말 코스",
                    contents = "재밌는 코스",
                    placeIds = listOf(100L, 101L),
                    visitDate = LocalDate.of(2026, 3, 1),
                ),
            )

        assertThat(result).isEqualTo(10L)
    }
}
