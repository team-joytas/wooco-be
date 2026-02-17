package kr.wooco.woocobe.core.user.application.service

import kr.wooco.woocobe.core.common.fixtures.UserFixtures
import kr.wooco.woocobe.core.course.application.port.out.CourseLikeQueryPort
import kr.wooco.woocobe.core.course.application.port.out.CourseQueryPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.user.application.port.`in`.ReadUserDetailsUseCase
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class UserQueryServiceTest {
    @Mock
    lateinit var userQueryPort: UserQueryPort

    @Mock
    lateinit var courseQueryPort: CourseQueryPort

    @Mock
    lateinit var courseLikeQueryPort: CourseLikeQueryPort

    @Mock
    lateinit var placeReviewQueryPort: PlaceReviewQueryPort

    private lateinit var userQueryService: UserQueryService

    @BeforeEach
    fun setUp() {
        userQueryService =
            UserQueryService(
                userQueryPort = userQueryPort,
                courseQueryPort = courseQueryPort,
                courseLikeQueryPort = courseLikeQueryPort,
                placeReviewQueryPort = placeReviewQueryPort,
            )
    }

    @Test
    @DisplayName("유저 상세 조회시 좋아요 수는 활성 코스 기준 집계 결과를 사용한다")
    fun readUserDetailsUsesFilteredLikeCount() {
        val user = UserFixtures.activeUser(id = 1L)
        given(userQueryPort.getByUserId(1L)).willReturn(user)
        given(placeReviewQueryPort.countByUserId(1L)).willReturn(3L)
        given(courseQueryPort.countByUserId(1L)).willReturn(2L)
        given(courseLikeQueryPort.countByUserId(1L)).willReturn(1L)

        val result = userQueryService.readUserDetails(ReadUserDetailsUseCase.Query(1L))

        assertThat(result.interestCourseCount).isEqualTo(1L)
        assertThat(result.courseCount).isEqualTo(2L)
        assertThat(result.reviewCount).isEqualTo(3L)
    }
}
