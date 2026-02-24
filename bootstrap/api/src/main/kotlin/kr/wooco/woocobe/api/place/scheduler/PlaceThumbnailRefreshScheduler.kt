package kr.wooco.woocobe.api.place.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import io.micrometer.core.instrument.MeterRegistry
import kr.wooco.woocobe.core.place.application.port.`in`.ReadPlacesNeedingThumbnailRefreshUseCase
import kr.wooco.woocobe.core.place.application.port.`in`.RefreshPlaceThumbnailUseCase
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicInteger

private val log = KotlinLogging.logger {}

@Component
class PlaceThumbnailRefreshScheduler(
    private val readPlacesNeedingThumbnailRefreshUseCase: ReadPlacesNeedingThumbnailRefreshUseCase,
    private val refreshPlaceThumbnailUseCase: RefreshPlaceThumbnailUseCase,
    private val meterRegistry: MeterRegistry,
) {
    @Scheduled(cron = "0 0 * * * *")
    @SchedulerLock(name = "refreshExpiredThumbnails", lockAtLeastFor = "PT1M", lockAtMostFor = "PT10M")
    fun refreshExpiredThumbnails() {
        val placeIds = readPlacesNeedingThumbnailRefreshUseCase.readPlacesNeedingThumbnailRefresh(
            ReadPlacesNeedingThumbnailRefreshUseCase.Query(BATCH_SIZE),
        )
        if (placeIds.isEmpty()) {
            log.info { "No places need thumbnail refresh" }
            return
        }

        log.info { "Starting thumbnail refresh for ${placeIds.size} places" }

        val semaphore = Semaphore(CONCURRENCY)
        val successCount = AtomicInteger(0)
        val failCount = AtomicInteger(0)

        Executors.newVirtualThreadPerTaskExecutor().use { executor ->
            placeIds.forEach { placeId ->
                executor.submit {
                    semaphore.acquire()
                    try {
                        refreshPlaceThumbnailUseCase.refreshPlaceThumbnail(
                            RefreshPlaceThumbnailUseCase.Command(placeId),
                        )
                        successCount.incrementAndGet()
                        meterRegistry.counter(METRIC_SUCCESS).increment()
                    } catch (e: Exception) {
                        failCount.incrementAndGet()
                        meterRegistry.counter(METRIC_FAILURE).increment()
                        log.warn { "Thumbnail refresh failed: placeId=$placeId, error=${e.message}" }
                    } finally {
                        semaphore.release()
                        Thread.sleep(DELAY_MS)
                    }
                }
            }
        }

        log.info {
            "Thumbnail refresh completed: success=${successCount.get()}, failed=${failCount.get()}, total=${placeIds.size}"
        }
    }

    companion object {
        const val BATCH_SIZE = 100
        const val CONCURRENCY = 10
        const val DELAY_MS = 200L
        const val METRIC_SUCCESS = "wooco.thumbnail.refresh.success"
        const val METRIC_FAILURE = "wooco.thumbnail.refresh.failure"
    }
}
