package kr.wooco.woocobe.core.placeReview.aplication.service

import kr.wooco.woocobe.core.common.fixtures.PlaceReviewFixtures
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewCommandPort
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.application.service.PlaceReviewCommandService
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.exception.InvalidPlaceReviewWriterException
import kr.wooco.woocobe.core.placereview.domain.exception.NotExistsPlaceReviewException
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceReviewCommandServiceTest {
    @Mock
    lateinit var placeReviewQueryPort: PlaceReviewQueryPort

    @Mock
    lateinit var placeReviewCommandPort: PlaceReviewCommandPort

    private lateinit var placeReviewCommandService: PlaceReviewCommandService

    @BeforeEach
    fun setUp() {
        placeReviewCommandService =
            PlaceReviewCommandService(
                placeReviewQueryPort = placeReviewQueryPort,
                placeReviewCommandPort = placeReviewCommandPort,
            )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T = Mockito.any<T>()

    /**
     * createPlaceReview 실패 – 저장 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 생성 중 저장 과정에서 예외가 발생하면 그대로 전파한다")
    fun createPlaceReviewSaveFail() {
        // given
        given(placeReviewCommandPort.savePlaceReview(any()))
            .willThrow(IllegalStateException("save error"))

        val command =
            PlaceReviewFixtures.createCommand(
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
                placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID,
                rating = PlaceReviewRating.FIVE.score,
                contents = "최고",
                oneLineReviews = listOf("친절해요"),
                imageUrls = emptyList(),
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewCommandService.createPlaceReview(command)
        }

        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())
    }

    /**
     * updatePlaceReview 실패 – 조회 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 수정 중 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun updatePlaceReviewFailWhenGetByPlaceReviewIdThrows() {
        // given
        val existing = PlaceReviewFixtures.activePlaceReview(userId = PlaceReviewFixtures.DEFAULT_USER_ID)

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willThrow(IllegalStateException("read error"))

        val command =
            PlaceReviewFixtures.updateCommand(
                placeReviewId = existing.id,
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
                rating = PlaceReviewRating.FOUR.score,
                contents = "수정했어요",
                oneLineReviews = listOf("조용해요"),
                imageUrls = emptyList(),
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewCommandService.updatePlaceReview(command)
        }

        verify(placeReviewCommandPort, never()).savePlaceReview(any())
    }

    /**
     * updatePlaceReview 실패 – 저장 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 수정 중 저장 과정에서 예외가 발생하면 그대로 전파한다")
    fun updatePlaceReviewFailWhenSaveThrows() {
        // given
        val existing = PlaceReviewFixtures.activePlaceReview(userId = PlaceReviewFixtures.DEFAULT_USER_ID)

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        given(placeReviewCommandPort.savePlaceReview(any()))
            .willThrow(IllegalStateException("save error"))

        val command =
            PlaceReviewFixtures.updateCommand(
                placeReviewId = existing.id,
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
                rating = PlaceReviewRating.FOUR.score,
                contents = "수정했어요",
                oneLineReviews = listOf("조용해요", "커피굿"),
                imageUrls = emptyList(),
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewCommandService.updatePlaceReview(command)
        }

        verify(placeReviewQueryPort, times(1)).getByPlaceReviewId(existing.id)
        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())
    }

    /**
     * updatePlaceReview 실패 – 작성자가 아니면 InvalidPlaceReviewWriterException을 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 수정 시 작성자가 아니면 InvalidPlaceReviewWriterException을 전파한다")
    fun updatePlaceReviewInvalidWriter() {
        // given
        val existing =
            PlaceReviewFixtures.activePlaceReview(
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
            )

        val attackerId = PlaceReviewFixtures.DEFAULT_USER_ID + PlaceReviewFixtures.DEFAULT_PLACE_ID

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        val command =
            PlaceReviewFixtures.updateCommand(
                placeReviewId = existing.id,
                userId = attackerId,
                rating = PlaceReviewRating.FOUR.score,
                contents = "해킹 수정",
                oneLineReviews = listOf("조용해요"),
                imageUrls = emptyList(),
            )

        // when & then
        assertThrows<InvalidPlaceReviewWriterException> {
            placeReviewCommandService.updatePlaceReview(command)
        }

        verify(placeReviewCommandPort, never()).savePlaceReview(any())
    }

    /**
     * deletePlaceReview 실패 – 조회 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 삭제 중 조회 과정에서 예외가 발생하면 그대로 전파한다")
    fun deletePlaceReviewFailWhenGetByPlaceReviewIdThrows() {
        // given
        val existing = PlaceReviewFixtures.activePlaceReview(userId = PlaceReviewFixtures.DEFAULT_USER_ID)

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willThrow(IllegalStateException("read error"))

        val command =
            PlaceReviewFixtures.deleteCommand(
                placeReviewId = existing.id,
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewCommandService.deletePlaceReview(command)
        }

        verify(placeReviewCommandPort, never()).savePlaceReview(any())
    }

    /**
     * deletePlaceReview 실패 – 저장 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 삭제 중 저장 과정에서 예외가 발생하면 그대로 전파한다")
    fun deletePlaceReviewFailWhenSaveThrows() {
        // given
        val existing = PlaceReviewFixtures.activePlaceReview(userId = PlaceReviewFixtures.DEFAULT_USER_ID)

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        given(placeReviewCommandPort.savePlaceReview(any()))
            .willThrow(IllegalStateException("save error"))

        val command =
            PlaceReviewFixtures.deleteCommand(
                placeReviewId = existing.id,
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeReviewCommandService.deletePlaceReview(command)
        }

        verify(placeReviewQueryPort, times(1)).getByPlaceReviewId(existing.id)
        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())
    }

    /**
     * deletePlaceReview 실패 – 이미 삭제된 리뷰면 NotExistsPlaceReviewException을 전파
     */
    @Test
    @DisplayName("실패: 장소 리뷰 삭제 시 이미 삭제된 리뷰면 NotExistsPlaceReviewException을 전파한다")
    fun deletePlaceReviewAlreadyDeleted() {
        // given
        val existing =
            PlaceReviewFixtures.deletedPlaceReview(
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
                placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID,
            )

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        val command =
            PlaceReviewFixtures.deleteCommand(
                placeReviewId = existing.id,
                userId = PlaceReviewFixtures.DEFAULT_USER_ID,
            )

        // when & then
        assertThrows<NotExistsPlaceReviewException> {
            placeReviewCommandService.deletePlaceReview(command)
        }

        verify(placeReviewCommandPort, never()).savePlaceReview(any())
    }

    /**
     * createPlaceReview 성공 – 저장된 ID를 반환하고, 저장 요청에 전달된 엔티티 필드를 검증
     */
    @Test
    @DisplayName("성공: 장소 리뷰 생성 시 savePlaceReview가 반환한 ID를 돌려준다")
    fun createPlaceReviewSuccess() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID
        val placeId = PlaceReviewFixtures.DEFAULT_PLACE_ID

        var savedArg: PlaceReview? = null
        val savedId = placeId

        given(placeReviewCommandPort.savePlaceReview(any()))
            .willAnswer { invocation ->
                val arg = invocation.getArgument<PlaceReview>(0)
                savedArg = arg
                savedId
            }

        val command =
            PlaceReviewFixtures.createCommand(
                userId = userId,
                placeId = placeId,
                rating = PlaceReviewRating.FOUR.score,
                contents = "분위기 좋아요",
                oneLineReviews = listOf("조용해요", "조용해요", "커피굿"),
                imageUrls = listOf("https://img.wooco.com/first.png", "https://img.wooco.com/second.png"),
            )

        // when
        val result = placeReviewCommandService.createPlaceReview(command)

        // then
        assertThat(result).isEqualTo(savedId)
        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())

        val saved = requireNotNull(savedArg)
        assertThat(saved.id).isEqualTo(0L)
        assertThat(saved.userId).isEqualTo(userId)
        assertThat(saved.placeId).isEqualTo(placeId)
        assertThat(saved.rating).isEqualTo(PlaceReviewRating.FOUR)
        assertThat(saved.contents.value).isEqualTo("분위기 좋아요")
        assertThat(saved.oneLineReviews.map { it.value }).containsExactlyInAnyOrder("조용해요", "커피굿")
        assertThat(saved.imageUrls).containsExactly(
            "https://img.wooco.com/first.png",
            "https://img.wooco.com/second.png",
        )
        assertThat(saved.status).isEqualTo(PlaceReview.Status.ACTIVE)
    }

    /**
     * updatePlaceReview 성공 – 작성자 검증을 통과하면 변경된 리뷰를 저장
     */
    @Test
    @DisplayName("성공: 장소 리뷰 수정 시 변경된 리뷰를 저장한다")
    fun updatePlaceReviewSuccess() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID
        val existing =
            PlaceReviewFixtures.activePlaceReview(
                userId = userId,
                rating = PlaceReviewRating.TWO,
                contents = "그냥 그래요",
                oneLineReviews = listOf("별로에요"),
                imageUrls = listOf("https://img.wooco.com/old.png"),
            )

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        var savedArg: PlaceReview? = null

        given(placeReviewCommandPort.savePlaceReview(any()))
            .willAnswer { invocation ->
                savedArg = invocation.getArgument(0)
                existing.id
            }

        val command =
            PlaceReviewFixtures.updateCommand(
                placeReviewId = existing.id,
                userId = userId,
                rating = PlaceReviewRating.FOUR.score,
                contents = "수정했어요",
                oneLineReviews = listOf("조용해요", "조용해요", "커피굿"),
                imageUrls = listOf("https://img.wooco.com/new.png"),
            )

        // when
        placeReviewCommandService.updatePlaceReview(command)

        // then
        verify(placeReviewQueryPort, times(1)).getByPlaceReviewId(existing.id)
        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())

        val saved = requireNotNull(savedArg)
        assertThat(saved.id).isEqualTo(existing.id)
        assertThat(saved.userId).isEqualTo(userId)
        assertThat(saved.rating).isEqualTo(PlaceReviewRating.FOUR)
        assertThat(saved.contents.value).isEqualTo("수정했어요")
        assertThat(saved.oneLineReviews.map { it.value }).containsExactlyInAnyOrder("조용해요", "커피굿")
        assertThat(saved.imageUrls).containsExactly("https://img.wooco.com/new.png")
        assertThat(saved.status).isEqualTo(PlaceReview.Status.ACTIVE)
    }

    /**
     * deletePlaceReview 성공 – 작성자 검증을 통과하면 DELETED 상태로 저장
     */
    @Test
    @DisplayName("성공: 장소 리뷰 삭제 시 DELETED 상태로 저장한다")
    fun deletePlaceReviewSuccess() {
        // given
        val userId = PlaceReviewFixtures.DEFAULT_USER_ID
        val existing =
            PlaceReviewFixtures.activePlaceReview(
                userId = userId,
            )

        given(placeReviewQueryPort.getByPlaceReviewId(existing.id))
            .willReturn(existing)

        var savedArg: PlaceReview? = null

        given(placeReviewCommandPort.savePlaceReview(any()))
            .willAnswer { invocation ->
                savedArg = invocation.getArgument(0)
                existing.id
            }

        val command =
            PlaceReviewFixtures.deleteCommand(
                placeReviewId = existing.id,
                userId = userId,
            )

        // when
        placeReviewCommandService.deletePlaceReview(command)

        // then
        verify(placeReviewQueryPort, times(1)).getByPlaceReviewId(existing.id)
        verify(placeReviewCommandPort, times(1)).savePlaceReview(any())

        val saved = requireNotNull(savedArg)
        assertThat(saved.id).isEqualTo(existing.id)
        assertThat(saved.status).isEqualTo(PlaceReview.Status.DELETED)
    }
}
