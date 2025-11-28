package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlaceWithPlaceReviewsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.result.PlaceResult
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceOneLineReviewStat
import kr.wooco.woocobe.core.placereview.domain.entity.PlaceReview
import kr.wooco.woocobe.core.placereview.domain.vo.PlaceReviewRating
import kr.wooco.woocobe.core.user.application.port.out.UserQueryPort
import kr.wooco.woocobe.core.user.domain.entity.User
import kr.wooco.woocobe.core.user.domain.vo.SocialType
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import kr.wooco.woocobe.core.user.domain.vo.UserProfile
import kr.wooco.woocobe.core.user.domain.vo.UserStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.willThrow
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class PlaceQueryServiceTest {
    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    @Mock
    lateinit var placeReviewQueryPort: PlaceReviewQueryPort

    @Mock
    lateinit var userQueryPort: UserQueryPort

    private lateinit var placeQueryService: PlaceQueryService

    @BeforeEach
    fun setUp() {
        placeQueryService =
            PlaceQueryService(
                placeQueryPort = placeQueryPort,
                placeReviewQueryPort = placeReviewQueryPort,
                userQueryPort = userQueryPort,
            )
    }

    private fun place(
        id: Long = 1L,
        name: String = "우코 카페",
        latitude: Double = 37.0,
        longitude: Double = 127.0,
        address: String = "서울 어딘가",
        kakaoPlaceId: String = "kakao-123",
        averageRating: Double = 4.5,
        reviewCount: Long = 3L,
        phoneNumber: String = "010-0000-0000",
        thumbnailUrl: String = "https://image.wooco.com/thumb.png",
    ): Place =
        Place(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude,
            address = address,
            kakaoPlaceId = kakaoPlaceId,
            averageRating = averageRating,
            reviewCount = reviewCount,
            phoneNumber = phoneNumber,
            thumbnailUrl = thumbnailUrl,
        )

    private fun placeReview(
        id: Long = 10L,
        userId: Long = 100L,
        placeId: Long = 1L,
        rating: PlaceReviewRating = PlaceReviewRating.FOUR,
        contents: String = "좋았어요",
        oneLineReviews: List<String> = listOf("조용해요"),
        status: PlaceReview.Status = PlaceReview.Status.ACTIVE,
    ): PlaceReview =
        PlaceReview(
            id = id,
            userId = userId,
            placeId = placeId,
            writeDateTime = LocalDateTime.now(),
            rating = rating,
            contents = PlaceReview.Contents(contents),
            oneLineReviews = oneLineReviews.map { PlaceReview.OneLineReview(it) },
            imageUrls = emptyList(),
            status = status,
        )

    private fun user(
        id: Long = 100L,
        name: String = "우코유저",
        status: UserStatus = UserStatus.ACTIVE,
    ): User =
        User(
            id = id,
            profile =
                UserProfile(
                    name = name,
                    profileUrl = "https://image.wooco.com/profile/.png",
                    description = "",
                ),
            status = status,
            socialUser =
                SocialUser(
                    socialId = "social-100L",
                    socialType = SocialType.KAKAO,
                ),
        )

    /**
     * readPlace 실패 – 장소가 없으면 NotExistsPlaceException을 전파.
     */
    @Test
    @DisplayName("실패: 단일 장소 조회 시 장소가 없으면 NotExistsPlaceException을 전파한다")
    fun readPlaceNotFound() {
        // given
        val placeId = 999L
        willThrow(NotExistsPlaceException::class.java)
            .given(placeQueryPort)
            .getByPlaceId(placeId)

        val query = ReadPlaceUseCase.Query(placeId = placeId)

        // when & then
        assertThrows<NotExistsPlaceException> {
            placeQueryService.readPlace(query)
        }

        verify(placeReviewQueryPort, never()).getAllPlaceOneLineReviewStatsByPlaceId(anyLong())
    }

    /**
     * readPlaceWithPlaceReviews 실패 – 장소가 없으면 NotExistsPlaceException을 전파
     */
    @Test
    @DisplayName("실패: 장소와 리뷰 목록 조회 시 장소가 없으면 NotExistsPlaceException을 전파한다")
    fun readPlaceWithReviewsNotFound() {
        // given
        val placeId = 999L
        willThrow(NotExistsPlaceException::class.java)
            .given(placeQueryPort)
            .getByPlaceId(placeId)

        val query = ReadPlaceWithPlaceReviewsUseCase.Query(placeId = placeId)

        // when & then
        assertThrows<NotExistsPlaceException> {
            placeQueryService.readPlaceWithPlaceReviews(query)
        }

        verify(placeReviewQueryPort, never()).getAllPlaceOneLineReviewStatsByPlaceId(anyLong())
        verify(placeReviewQueryPort, never()).getRecent2ByPlaceId(anyLong())
        verify(userQueryPort, never()).getAllByUserIds(anyList())
    }

    /**
     * readPlace 성공 – Place와 한줄평 통계를 합쳐 반환
     */
    @Test
    @DisplayName("성공: 단일 장소 조회 시 Place와 한줄평 통계를 합쳐 반환한다")
    fun readPlaceSuccess() {
        // given
        val placeId = 1L
        val place = place(id = placeId)
        val stats =
            listOf(
                PlaceOneLineReviewStat(contents = "분위기 좋아요", count = 2L),
                PlaceOneLineReviewStat(contents = "커피 맛있음", count = 1L),
            )

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(place)
        given(placeReviewQueryPort.getAllPlaceOneLineReviewStatsByPlaceId(placeId))
            .willReturn(stats)

        val query = ReadPlaceUseCase.Query(placeId = placeId)

        // when
        val result: PlaceResult = placeQueryService.readPlace(query)

        // then
        assertThat(result.placeId).isEqualTo(placeId)
        assertThat(result.placeName).isEqualTo("우코 카페")
        assertThat(result.kakaoPlaceId).isEqualTo("kakao-123")
        assertThat(result.averageRating).isEqualTo(4.5)
        assertThat(result.reviewCount).isEqualTo(3L)
        assertThat(result.phoneNumber).isEqualTo("010-0000-0000")
        assertThat(result.thumbnailUrl).isEqualTo("https://image.wooco.com/thumb.png")
        assertThat(result.placeOneLineReviewStats)
            .hasSize(2)
            .extracting<String> { it.contents }
            .containsExactlyInAnyOrder("분위기 좋아요", "커피 맛있음")

        verify(placeQueryPort, times(1)).getByPlaceId(placeId)
        verify(placeReviewQueryPort, times(1)).getAllPlaceOneLineReviewStatsByPlaceId(placeId)
    }

    /**
     * readPlaceWithPlaceReviews 성공 - 장소와 최근 리뷰 및 작성자 정보를 함께 반환
     */
    @Test
    @DisplayName("성공: 장소와 리뷰 목록 조회 시 최근 리뷰와 작성자 정보를 함께 반환한다")
    fun readPlaceWithReviewsSuccessWithReviews() {
        // given
        val placeId = 1L
        val place = place(id = placeId)

        val stats =
            listOf(
                PlaceOneLineReviewStat(contents = "조용해요", count = 1L),
                PlaceOneLineReviewStat(contents = "커피가 맛있어요", count = 2L),
            )

        val reviewerId = 100L

        val review1 = placeReview(id = 10L, userId = reviewerId, placeId = placeId)
        val review2 = placeReview(id = 11L, userId = reviewerId, placeId = placeId)

        val writer = user(id = reviewerId, name = "우코유저")

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(place)
        given(placeReviewQueryPort.getAllPlaceOneLineReviewStatsByPlaceId(placeId))
            .willReturn(stats)
        given(placeReviewQueryPort.getRecent2ByPlaceId(placeId))
            .willReturn(listOf(review1, review2))
        given(userQueryPort.getAllByUserIds(listOf(reviewerId)))
            .willReturn(listOf(writer))

        val query = ReadPlaceWithPlaceReviewsUseCase.Query(placeId = placeId)

        // when
        val result = placeQueryService.readPlaceWithPlaceReviews(query)

        // then
        assertThat(result.place.placeId).isEqualTo(placeId)
        assertThat(result.placeReviews).hasSize(2)

        verify(placeQueryPort).getByPlaceId(placeId)
        verify(placeReviewQueryPort).getAllPlaceOneLineReviewStatsByPlaceId(placeId)
        verify(placeReviewQueryPort).getRecent2ByPlaceId(placeId)
        verify(userQueryPort).getAllByUserIds(listOf(reviewerId))
    }

    /**
     * readPlaceWithPlaceReviews 성공 – 리뷰가 하나도 없을 때 placeReviews와 한줄평 통계는 모두 빈 리스트를 반환
     */
    @Test
    @DisplayName("성공: 장소와 리뷰 목록 조회 시 리뷰가 없으면 빈 리스트를 반환한다")
    fun readPlaceWithPlaceReviewsSuccessWithoutReviews() {
        // given
        val placeId = 1L
        val place = place(id = placeId)

        val emptyStats: List<PlaceOneLineReviewStat> = emptyList()

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(place)
        given(placeReviewQueryPort.getAllPlaceOneLineReviewStatsByPlaceId(placeId))
            .willReturn(emptyStats)
        given(placeReviewQueryPort.getRecent2ByPlaceId(placeId))
            .willReturn(emptyList())
        given(userQueryPort.getAllByUserIds(emptyList()))
            .willReturn(emptyList())

        val query = ReadPlaceWithPlaceReviewsUseCase.Query(placeId = placeId)

        // when
        val result = placeQueryService.readPlaceWithPlaceReviews(query)

        // then
        assertThat(result.place.placeId).isEqualTo(placeId)
        assertThat(result.place.placeOneLineReviewStats).isEmpty()
        assertThat(result.placeReviews).isEmpty()

        verify(placeQueryPort).getByPlaceId(placeId)
        verify(placeReviewQueryPort).getAllPlaceOneLineReviewStatsByPlaceId(placeId)
        verify(placeReviewQueryPort).getRecent2ByPlaceId(placeId)
        verify(userQueryPort).getAllByUserIds(emptyList())
    }
}
