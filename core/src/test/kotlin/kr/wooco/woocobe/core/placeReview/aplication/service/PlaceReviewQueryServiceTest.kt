package kr.wooco.woocobe.core.placereview.application.service

import kr.wooco.woocobe.core.common.fixtures.PlaceFixtures
import kr.wooco.woocobe.core.common.fixtures.PlaceReviewFixtures
import kr.wooco.woocobe.core.common.fixtures.UserFixtures
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.placereview.application.port.`in`.ExistsPlaceReviewWriterUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadAllUserPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.ReadPlaceReviewUseCase
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithPlaceResult
import kr.wooco.woocobe.core.placereview.application.port.`in`.result.PlaceReviewWithWriterResult
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceReviewQueryServiceTest {
    @Mock
    lateinit var userQueryPort: UserQueryPort

    @Mock
    lateinit var placeReviewQueryPort: PlaceReviewQueryPort

    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    private lateinit var placeReviewQueryService: PlaceReviewQueryService

    @BeforeEach
    fun setUp() {
        placeReviewQueryService =
            PlaceReviewQueryService(
                userQueryPort = userQueryPort,
                placeReviewQueryPort = placeReviewQueryPort,
                placeQueryPort = placeQueryPort,
            )
    }

    /**
     * readAllPlaceReview 실패 - placeReviewQueryPort 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 장소 리뷰 전체 조회 중 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readAllPlaceReviewFailWhenGetAllByPlaceIdThrows() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID
        val exception = IllegalStateException("read error")

        given(placeReviewQueryPort.getAllByPlaceId(placeId))
            .willThrow(exception)

        val query = ReadAllPlaceReviewUseCase.Query(placeId = placeId)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readAllPlaceReview(query)
        }

        verify(placeReviewQueryPort).getAllByPlaceId(placeId)
        verify(userQueryPort, never()).getAllByUserIds(anyList())
    }

    /**
     * readAllPlaceReview 실패 - 작성자 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 장소 리뷰 전체 조회 중 작성자 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readAllPlaceReviewFailWhenGetAllByUserIdsThrows() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID
        val writerId = PlaceReviewFixtures.DEFAULT_USER_ID

        val firstReview = PlaceReviewFixtures.activePlaceReview(userId = writerId, placeId = placeId)
        val secondReview = PlaceReviewFixtures.activePlaceReview(userId = writerId, placeId = placeId)

        given(placeReviewQueryPort.getAllByPlaceId(placeId))
            .willReturn(listOf(firstReview, secondReview))

        given(userQueryPort.getAllByUserIds(listOf(writerId)))
            .willThrow(IllegalStateException("read error"))

        val query = ReadAllPlaceReviewUseCase.Query(placeId = placeId)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readAllPlaceReview(query)
        }

        verify(placeReviewQueryPort).getAllByPlaceId(placeId)
        verify(userQueryPort).getAllByUserIds(listOf(writerId))
    }

    /**
     * readAllUserPlaceReview 실패 - placeReviewQueryPort 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 유저 리뷰 전체 조회 중 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readAllUserPlaceReviewFailWhenGetAllByUserIdThrows() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID

        given(placeReviewQueryPort.getAllByUserId(userId))
            .willThrow(IllegalStateException("read error"))

        val query = ReadAllUserPlaceReviewUseCase.Query(userId = userId)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readAllUserPlaceReview(query)
        }

        verify(placeReviewQueryPort).getAllByUserId(userId)
        verify(placeQueryPort, never()).getAllByPlaceIds(anyList())
    }

    /**
     * readAllUserPlaceReview 실패 - 장소 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 유저 리뷰 전체 조회 중 장소 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readAllUserPlaceReviewFailWhenGetAllByPlaceIdsThrows() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID

        val firstReview = PlaceReviewFixtures.activePlaceReview(userId = userId, placeId = placeId)
        val secondReview = PlaceReviewFixtures.activePlaceReview(userId = userId, placeId = placeId)

        given(placeReviewQueryPort.getAllByUserId(userId))
            .willReturn(listOf(firstReview, secondReview))

        given(placeQueryPort.getAllByPlaceIds(listOf(placeId)))
            .willThrow(IllegalStateException("read error"))

        val query = ReadAllUserPlaceReviewUseCase.Query(userId = userId)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readAllUserPlaceReview(query)
        }

        verify(placeReviewQueryPort).getAllByUserId(userId)
        verify(placeQueryPort).getAllByPlaceIds(listOf(placeId))
    }

    /**
     * readPlaceReview 실패 - placeReviewQueryPort 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 단일 리뷰 조회 중 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readPlaceReviewFailWhenGetByPlaceReviewIdThrows() {
        // given
        val review = PlaceReviewFixtures.activePlaceReview()
        val exception = IllegalStateException("read error")

        given(placeReviewQueryPort.getByPlaceReviewId(review.id))
            .willThrow(exception)

        val query = ReadPlaceReviewUseCase.Query(placeReviewId = review.id)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readPlaceReview(query)
        }

        verify(placeReviewQueryPort).getByPlaceReviewId(review.id)
        verify(userQueryPort, never()).getByUserId(review.userId)
    }

    /**
     * readPlaceReview 실패 - 작성자 조회 과정에서 예외가 발생하면 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 단일 리뷰 조회 중 작성자 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun readPlaceReviewFailWhenGetByUserIdThrows() {
        // given
        val writerId = PlaceReviewFixtures.DEFAULT_USER_ID
        val review = PlaceReviewFixtures.activePlaceReview(userId = writerId)

        given(placeReviewQueryPort.getByPlaceReviewId(review.id))
            .willReturn(review)

        given(userQueryPort.getByUserId(writerId))
            .willThrow(IllegalStateException("read error"))

        val query = ReadPlaceReviewUseCase.Query(placeReviewId = review.id)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.readPlaceReview(query)
        }

        verify(placeReviewQueryPort).getByPlaceReviewId(review.id)
        verify(userQueryPort).getByUserId(writerId)
    }

    /**
     * existsPlaceReviewWriter 실패 - QueryService는 판단하지 않고 QueryPort에 위임하므로, 예외도 그대로 전파한다
     */
    @Test
    @DisplayName("실패: 리뷰 작성자 존재 여부 조회 중 예외가 발생하면 그대로 전파한다")
    fun existsPlaceReviewWriterFailWhenPortThrows() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID

        given(placeReviewQueryPort.existsByPlaceIdAndUserId(placeId, userId))
            .willThrow(IllegalStateException("read error"))

        val query = ExistsPlaceReviewWriterUseCase.Query(placeId = placeId, userId = userId)

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewQueryService.existsPlaceReviewWriter(query)
        }

        verify(placeReviewQueryPort).existsByPlaceIdAndUserId(placeId, userId)
        verify(userQueryPort, never()).getAllByUserIds(anyList())
        verify(placeQueryPort, never()).getAllByPlaceIds(anyList())
    }

    /**
     * readAllPlaceReview 성공
     * - 동일한 작성자가 여러 리뷰를 작성해도 작성자 조회는 중복 제거
     * - 리뷰 + 작성자 정보를 합쳐 반환
     */
    @Test
    @DisplayName("성공: 장소 리뷰 전체 조회 시 리뷰와 작성자 정보를 합쳐 반환한다")
    fun readAllPlaceReviewSuccess() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID
        val writerId = PlaceReviewFixtures.DEFAULT_USER_ID

        val firstReview = PlaceReviewFixtures.activePlaceReview(userId = writerId, placeId = placeId)
        val secondReview = PlaceReviewFixtures.activePlaceReview(userId = writerId, placeId = placeId)

        given(placeReviewQueryPort.getAllByPlaceId(placeId))
            .willReturn(listOf(firstReview, secondReview))

        val writer = UserFixtures.activeUser(id = writerId)
        given(userQueryPort.getAllByUserIds(listOf(writerId)))
            .willReturn(listOf(writer))

        val query = ReadAllPlaceReviewUseCase.Query(placeId = placeId)

        // when
        val result = placeReviewQueryService.readAllPlaceReview(query)

        // then
        val expected =
            PlaceReviewWithWriterResult.listOf(
                listOf(firstReview, secondReview),
                listOf(writer),
            )

        assertThat(result).isEqualTo(expected)

        verify(placeReviewQueryPort).getAllByPlaceId(placeId)
        verify(userQueryPort).getAllByUserIds(listOf(writerId))
    }

    /**
     * readAllPlaceReview 성공
     * - 리뷰가 없으면 작성자 조회는 빈 리스트로 호출
     * - 결과도 빈 리스트 반환
     */
    @Test
    @DisplayName("성공: 장소 리뷰가 없으면 빈 리스트를 반환한다")
    fun readAllPlaceReviewEmpty() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID

        given(placeReviewQueryPort.getAllByPlaceId(placeId))
            .willReturn(emptyList())

        given(userQueryPort.getAllByUserIds(emptyList()))
            .willReturn(emptyList())

        val query = ReadAllPlaceReviewUseCase.Query(placeId = placeId)

        // when
        val result = placeReviewQueryService.readAllPlaceReview(query)

        // then
        val expected = PlaceReviewWithWriterResult.listOf(emptyList(), emptyList())
        assertThat(result).isEqualTo(expected)

        verify(placeReviewQueryPort).getAllByPlaceId(placeId)
        verify(userQueryPort).getAllByUserIds(emptyList())
    }

    /**
     * readAllUserPlaceReview 성공
     * - 동일한 장소에 대한 리뷰가 여러 개여도 장소 조회는 중복 제거
     * - 리뷰 + 장소 정보를 합쳐 반환
     */
    @Test
    @DisplayName("성공: 유저 리뷰 전체 조회 시 리뷰와 장소 정보를 합쳐 반환한다")
    fun readAllUserPlaceReviewSuccess() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID

        val firstReview = PlaceReviewFixtures.activePlaceReview(userId = userId, placeId = placeId)
        val secondReview = PlaceReviewFixtures.activePlaceReview(userId = userId, placeId = placeId)

        given(placeReviewQueryPort.getAllByUserId(userId))
            .willReturn(listOf(firstReview, secondReview))

        val place = PlaceFixtures.place(id = placeId)
        given(placeQueryPort.getAllByPlaceIds(listOf(placeId)))
            .willReturn(listOf(place))

        val query = ReadAllUserPlaceReviewUseCase.Query(userId = userId)

        // when
        val result = placeReviewQueryService.readAllUserPlaceReview(query)

        // then
        val expected =
            PlaceReviewWithPlaceResult.listOf(
                listOf(firstReview, secondReview),
                listOf(place),
            )

        assertThat(result).isEqualTo(expected)

        verify(placeReviewQueryPort).getAllByUserId(userId)
        verify(placeQueryPort).getAllByPlaceIds(listOf(placeId))
    }

    /**
     * readPlaceReview 성공
     * - 단일 리뷰 조회 시 리뷰와 작성자 정보를 함께 반환
     */
    @Test
    @DisplayName("성공: 단일 리뷰 조회 시 리뷰와 작성자 정보를 반환한다")
    fun readPlaceReviewSuccess() {
        // given
        val writerId = PlaceReviewFixtures.DEFAULT_USER_ID
        val review = PlaceReviewFixtures.activePlaceReview(userId = writerId)

        given(placeReviewQueryPort.getByPlaceReviewId(review.id))
            .willReturn(review)

        val writer = UserFixtures.activeUser(id = writerId)
        given(userQueryPort.getByUserId(writerId))
            .willReturn(writer)

        val query = ReadPlaceReviewUseCase.Query(placeReviewId = review.id)

        // when
        val result = placeReviewQueryService.readPlaceReview(query)

        // then
        val expected = PlaceReviewWithWriterResult.of(review, writer)
        assertThat(result).isEqualTo(expected)

        verify(placeReviewQueryPort).getByPlaceReviewId(review.id)
        verify(userQueryPort).getByUserId(writerId)
    }

    /**
     * existsPlaceReviewWriter 성공
     * - QueryService는 판단하지 않고 QueryPort에 위임
     */
    @Test
    @DisplayName("성공: 리뷰 작성자 존재 여부 조회는 QueryPort에 위임한다")
    fun existsPlaceReviewWriterSuccess() {
        // given
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID

        given(placeReviewQueryPort.existsByPlaceIdAndUserId(placeId, userId))
            .willReturn(true)

        val query = ExistsPlaceReviewWriterUseCase.Query(placeId = placeId, userId = userId)

        // when
        val result = placeReviewQueryService.existsPlaceReviewWriter(query)

        // then
        assertThat(result).isTrue()

        verify(placeReviewQueryPort).existsByPlaceIdAndUserId(placeId, userId)
        verify(userQueryPort, never()).getAllByUserIds(anyList())
        verify(placeQueryPort, never()).getAllByPlaceIds(anyList())
    }
}
