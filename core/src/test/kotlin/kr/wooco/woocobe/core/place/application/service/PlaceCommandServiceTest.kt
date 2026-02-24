package kr.wooco.woocobe.core.place.application.service

import kr.wooco.woocobe.core.place.application.port.`in`.CreatePlaceIfNotExistsUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.RefreshPlaceThumbnailUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdateAverageRatingUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdatePlaceImageUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.UpdateReviewStatsUseCase
import kr.wooco.woocobe.core.place.application.port.out.PlaceClientPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceCommandPort
import kr.wooco.woocobe.core.place.application.port.out.PlaceQueryPort
import kr.wooco.woocobe.core.place.domain.entity.Place
import kr.wooco.woocobe.core.place.domain.exception.NotExistsPlaceException
import kr.wooco.woocobe.core.placereview.application.port.out.PlaceReviewQueryPort
import kr.wooco.woocobe.core.placereview.application.service.dto.PlaceReviewStats
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
class PlaceCommandServiceTest {
    @Mock
    lateinit var placeClientPort: PlaceClientPort

    @Mock
    lateinit var placeCommandPort: PlaceCommandPort

    @Mock
    lateinit var placeQueryPort: PlaceQueryPort

    @Mock
    lateinit var placeReviewQueryPort: PlaceReviewQueryPort

    private lateinit var placeCommandService: PlaceCommandService

    @BeforeEach
    fun setUp() {
        placeCommandService =
            PlaceCommandService(
                placeClientPort = placeClientPort,
                placeCommandPort = placeCommandPort,
                placeQueryPort = placeQueryPort,
                placeReviewQueryPort = placeReviewQueryPort,
            )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> any(): T = Mockito.any<T>()

    private fun place(
        id: Long = 1L,
        name: String = "우코 카페",
        latitude: Double = 37.0,
        longitude: Double = 127.0,
        address: String = "서울 어딘가",
        kakaoPlaceId: String = "kakao-123",
        averageRating: Double = 0.0,
        reviewCount: Long = 0L,
        phoneNumber: String = "010-0000-0000",
        thumbnailUrl: String = "",
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

    /**
     * createPlaceIfNotExists 실패 – 저장 과정에서 예외가 발생하면 그대로 전파
     */
    @Test
    @DisplayName("실패: 장소 생성 중 저장 과정에서 예외가 발생하면 그대로 전파한다")
    fun createSaveFail() {
        // given
        val kakaoPlaceId = "kakao-error"

        given(placeQueryPort.getOrNullByKakaoPlaceId(kakaoPlaceId))
            .willReturn(null)

        given(placeCommandPort.savePlace(any()))
            .willThrow(IllegalStateException("save error"))

        val command =
            CreatePlaceIfNotExistsUseCase.Command(
                name = "에러",
                latitude = 37.0,
                longitude = 127.0,
                address = "서울 어딘가",
                kakaoPlaceId = kakaoPlaceId,
                phoneNumber = "010-9999-9999",
            )

        // when & then
        assertThrows<IllegalStateException> {
            placeCommandService.createPlaceIfNotExists(command)
        }

        verify(placeCommandPort, times(1)).savePlace(any())
    }

    /**
     * updateReviewStats 실패 – 통계 갱신 시 장소가 없으면 NotExistsPlaceException을 전파
     */
    @Test
    @DisplayName("실패: 리뷰 통계 갱신 시 장소가 없으면 NotExistsPlaceException을 전파한다")
    fun updateStatsPlaceNotFound() {
        // given
        val placeId = 999L

        val stats =
            PlaceReviewStats(
                averageRating = 4.3,
                reviewCount = 7L,
            )

        given(placeReviewQueryPort.getPlaceReviewStatsByPlaceId(placeId))
            .willReturn(stats)

        given(placeQueryPort.getByPlaceId(placeId))
            .willThrow(NotExistsPlaceException)

        val command = UpdateReviewStatsUseCase.Command(placeId = placeId)

        // when & then
        assertThrows<NotExistsPlaceException> {
            placeCommandService.updateReviewStats(command)
        }

        verify(placeCommandPort, never()).savePlace(any())
    }

    /**
     * updateAverageRating 실패 – 평균 평점 갱신 시 장소가 없으면 NotExistsPlaceException을 전파
     */
    @Test
    @DisplayName("실패: 평균 평점 갱신 시 장소가 없으면 NotExistsPlaceException을 전파한다")
    fun updateAvgPlaceNotFound() {
        // given
        val placeId = 999L

        given(placeReviewQueryPort.getAverageRatingByPlaceId(placeId))
            .willReturn(3.8)

        given(placeQueryPort.getByPlaceId(placeId))
            .willThrow(NotExistsPlaceException)

        val command = UpdateAverageRatingUseCase.Command(placeId = placeId)

        // when & then
        assertThrows<NotExistsPlaceException> {
            placeCommandService.updateAverageRating(command)
        }

        verify(placeCommandPort, never()).savePlace(any())
    }

    /**
     * createPlaceIfNotExists 성공 – 동일 kakaoPlaceId가 이미 있으면 기존 placeId를 반환하고 새로 생성하지 않는다.
     */
    @Test
    @DisplayName("성공: 이미 장소가 존재하면 기존 placeId를 반환하고 새로 생성하지 않는다")
    fun createExists() {
        // given
        val kakaoPlaceId = "kakao-123"
        val existingPlace = place(id = 10L, kakaoPlaceId = kakaoPlaceId)

        given(placeQueryPort.getOrNullByKakaoPlaceId(kakaoPlaceId))
            .willReturn(existingPlace)

        val command =
            CreatePlaceIfNotExistsUseCase.Command(
                name = "우코 카페",
                latitude = 37.0,
                longitude = 127.0,
                address = "서울 어딘가",
                kakaoPlaceId = kakaoPlaceId,
                phoneNumber = "010-0000-0000",
            )

        // when
        val result = placeCommandService.createPlaceIfNotExists(command)

        // then
        assertThat(result).isEqualTo(10L)
        verify(placeCommandPort, never()).savePlace(any())
    }

    /**
     * createPlaceIfNotExists 성공 – 장소가 없으면 새 Place를 생성하고  ID 반환
     */
    @Test
    @DisplayName("성공: 존재하지 않으면 Place를 생성하고 placeId를 반환한다")
    fun createPlace() {
        // given
        val kakaoPlaceId = "kakao-999"

        given(placeQueryPort.getOrNullByKakaoPlaceId(kakaoPlaceId))
            .willReturn(null)

        var savedPlace: Place? = null

        given(placeCommandPort.savePlace(any()))
            .willAnswer { invocation ->
                val arg = invocation.getArgument<Place>(0)
                savedPlace = arg
                100L
            }

        val command =
            CreatePlaceIfNotExistsUseCase.Command(
                name = "새 우코 카페",
                latitude = 37.1,
                longitude = 127.1,
                address = "서울 어딘가",
                kakaoPlaceId = kakaoPlaceId,
                phoneNumber = "010-1234-5678",
            )

        // when
        val result = placeCommandService.createPlaceIfNotExists(command)

        // then
        assertThat(result).isEqualTo(100L)
        verify(placeCommandPort, times(1)).savePlace(any())

        val saved = requireNotNull(savedPlace)
        assertThat(saved.name).isEqualTo("새 우코 카페")
        assertThat(saved.address).isEqualTo("서울 어딘가")
        assertThat(saved.kakaoPlaceId).isEqualTo(kakaoPlaceId)
        assertThat(saved.phoneNumber).isEqualTo("010-1234-5678")
    }

    /**
     * updateReviewStats 성공 – 리뷰 통계 기반으로 평균 평점과 리뷰 수를 동시에 갱신
     */
    @Test
    @DisplayName("성공: 리뷰 통계로 평균 평점과 리뷰 수를 갱신한다")
    fun updateStats() {
        // given
        val placeId = 3L

        val stats =
            PlaceReviewStats(
                averageRating = 4.3,
                reviewCount = 7L,
            )

        given(placeReviewQueryPort.getPlaceReviewStatsByPlaceId(placeId))
            .willReturn(stats)

        val place =
            place(
                id = placeId,
                averageRating = 0.0,
                reviewCount = 0L,
            )

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(place)

        var savedPlace: Place? = null

        given(placeCommandPort.savePlace(any()))
            .willAnswer { invocation ->
                val arg = invocation.getArgument<Place>(0)
                savedPlace = arg
                1L
            }

        val command = UpdateReviewStatsUseCase.Command(placeId = placeId)

        // when
        placeCommandService.updateReviewStats(command)

        // then
        verify(placeCommandPort, times(1)).savePlace(any())

        val saved = requireNotNull(savedPlace)
        assertThat(saved.averageRating).isEqualTo(4.3)
        assertThat(saved.reviewCount).isEqualTo(7L)
    }

    /**
     * updateAverageRating 성공 – 평균 평점만 다시 계산해서 갱신
     */
    @Test
    @DisplayName("성공: 평균 평점만 다시 계산해서 갱신한다")
    fun updateAvg() {
        // given
        val placeId = 4L

        given(placeReviewQueryPort.getAverageRatingByPlaceId(placeId))
            .willReturn(3.8)

        val place =
            place(
                id = placeId,
                averageRating = 5.0,
                reviewCount = 10L,
            )

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(place)

        var savedPlace: Place? = null

        given(placeCommandPort.savePlace(any()))
            .willAnswer { invocation ->
                val arg = invocation.getArgument<Place>(0)
                savedPlace = arg
                1L
            }

        val command = UpdateAverageRatingUseCase.Command(placeId = placeId)

        // when
        placeCommandService.updateAverageRating(command)

        // then
        verify(placeCommandPort, times(1)).savePlace(any())

        val saved = requireNotNull(savedPlace)
        assertThat(saved.averageRating).isEqualTo(3.8)
        assertThat(saved.reviewCount).isEqualTo(10L)
    }

    /**
     * refreshPlaceThumbnail 성공 – 이미지 갱신 후 markThumbnailRefreshed 호출
     */
    @Test
    @DisplayName("성공: 썸네일 갱신 성공 시 markThumbnailRefreshed를 호출한다")
    fun refreshThumbnailSuccess() {
        // given
        val placeId = 1L
        val existingPlace = place(id = placeId, thumbnailUrl = "old-url")

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(existingPlace)
        given(placeClientPort.fetchPlaceThumbnailUrl(existingPlace.name, existingPlace.address))
            .willReturn("new-url")
        given(placeCommandPort.savePlace(any()))
            .willReturn(placeId)

        // when
        placeCommandService.refreshPlaceThumbnail(RefreshPlaceThumbnailUseCase.Command(placeId))

        // then
        verify(placeCommandPort).markThumbnailRefreshed(placeId)
        verify(placeCommandPort, never()).markThumbnailRefreshFailed(placeId)
    }

    /**
     * refreshPlaceThumbnail 실패 – Google API 예외 시 markThumbnailRefreshFailed 호출 후 rethrow
     */
    @Test
    @DisplayName("실패: 썸네일 갱신 중 예외 발생 시 markThumbnailRefreshFailed 후 예외를 전파한다")
    fun refreshThumbnailFail() {
        // given
        val placeId = 1L
        val existingPlace = place(id = placeId)

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(existingPlace)
        given(placeClientPort.fetchPlaceThumbnailUrl(existingPlace.name, existingPlace.address))
            .willThrow(RuntimeException("Google API error"))

        // when & then
        assertThrows<RuntimeException> {
            placeCommandService.refreshPlaceThumbnail(RefreshPlaceThumbnailUseCase.Command(placeId))
        }

        verify(placeCommandPort).markThumbnailRefreshFailed(placeId)
        verify(placeCommandPort, never()).markThumbnailRefreshed(placeId)
    }

    /**
     * refreshPlaceThumbnail 성공 – URL이 null이면 이미지 갱신 없이 markThumbnailRefreshed 호출
     */
    @Test
    @DisplayName("성공: Google API가 null 반환해도 markThumbnailRefreshed를 호출한다")
    fun refreshThumbnailNullUrl() {
        // given
        val placeId = 1L
        val existingPlace = place(id = placeId)

        given(placeQueryPort.getByPlaceId(placeId))
            .willReturn(existingPlace)
        given(placeClientPort.fetchPlaceThumbnailUrl(existingPlace.name, existingPlace.address))
            .willReturn(null)

        // when
        placeCommandService.refreshPlaceThumbnail(RefreshPlaceThumbnailUseCase.Command(placeId))

        // then
        verify(placeCommandPort).markThumbnailRefreshed(placeId)
        verify(placeCommandPort, never()).savePlace(any())
    }
}
