package kr.wooco.woocobe.api.place.scheduler

import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import kr.wooco.woocobe.api.place.scheduler.PlaceThumbnailRefreshScheduler.Companion.METRIC_FAILURE
import kr.wooco.woocobe.api.place.scheduler.PlaceThumbnailRefreshScheduler.Companion.METRIC_SUCCESS
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlacesNeedingThumbnailRefreshUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.RefreshPlaceThumbnailUseCase
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceThumbnailRefreshSchedulerTest {
    @Mock
    lateinit var readPlacesNeedingThumbnailRefreshUseCase: ReadPlacesNeedingThumbnailRefreshUseCase

    @Mock
    lateinit var refreshPlaceThumbnailUseCase: RefreshPlaceThumbnailUseCase

    private lateinit var meterRegistry: SimpleMeterRegistry
    private lateinit var scheduler: PlaceThumbnailRefreshScheduler

    @BeforeEach
    fun setUp() {
        meterRegistry = SimpleMeterRegistry()
        scheduler = PlaceThumbnailRefreshScheduler(
            readPlacesNeedingThumbnailRefreshUseCase = readPlacesNeedingThumbnailRefreshUseCase,
            refreshPlaceThumbnailUseCase = refreshPlaceThumbnailUseCase,
            meterRegistry = meterRegistry,
        )
    }

    @Test
    @DisplayName("만료된 장소의 썸네일을 성공적으로 갱신한다")
    fun `refreshes expired thumbnails successfully`() {
        val placeIds = listOf(1L, 2L, 3L)
        given(
            readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
                ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
            ),
        ).willReturn(placeIds)

        scheduler.refreshExpiredThumbnails()

        placeIds.forEach { placeId ->
            verify(refreshPlaceThumbnailUseCase).refreshPlaceThumbnail(
                RefreshPlaceThumbnailUseCase.Command(placeId),
            )
        }
        assertThat(meterRegistry.counter(METRIC_SUCCESS).count()).isEqualTo(3.0)
    }

    @Test
    @DisplayName("UseCase에서 예외 발생 시 실패 메트릭을 기록한다")
    fun `records failure metric on use case exception`() {
        val placeIds = listOf(1L)
        given(
            readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
                ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
            ),
        ).willReturn(placeIds)
        Mockito.doThrow(RuntimeException("Google API error"))
            .`when`(refreshPlaceThumbnailUseCase)
            .refreshPlaceThumbnail(RefreshPlaceThumbnailUseCase.Command(1L))

        scheduler.refreshExpiredThumbnails()

        assertThat(meterRegistry.counter(METRIC_FAILURE).count()).isEqualTo(1.0)
    }

    @Test
    @DisplayName("갱신 대상이 없으면 즉시 종료한다")
    fun `returns immediately when no places need refresh`() {
        given(
            readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
                ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
            ),
        ).willReturn(emptyList())

        scheduler.refreshExpiredThumbnails()

        assertThat(meterRegistry.counter(METRIC_SUCCESS).count()).isEqualTo(0.0)
        assertThat(meterRegistry.counter(METRIC_FAILURE).count()).isEqualTo(0.0)
    }

    @Test
    @DisplayName("BATCH_SIZE만큼만 조회한다")
    fun `respects batch size`() {
        given(
            readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
                ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
            ),
        ).willReturn(emptyList())

        scheduler.refreshExpiredThumbnails()

        verify(readPlacesNeedingThumbnailRefreshUseCase).readPlacesNeedingThumbnailRefresh(
            ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
        )
    }

    @Test
    @DisplayName("여러 장소를 병렬로 처리하고 메트릭을 올바르게 기록한다")
    fun `processes multiple places and records metrics`() {
        val placeIds = listOf(1L, 2L, 3L, 4L, 5L)
        given(
            readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
                ReadPlacesNeedingThumbnailRefreshUseCase.Query(PlaceThumbnailRefreshScheduler.BATCH_SIZE),
            ),
        ).willReturn(placeIds)

        scheduler.refreshExpiredThumbnails()

        assertThat(meterRegistry.counter(METRIC_SUCCESS).count()).isEqualTo(5.0)
        assertThat(meterRegistry.counter(METRIC_FAILURE).count()).isEqualTo(0.0)
    }
}
