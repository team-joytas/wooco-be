package kr.wooco.woocobe.metric.warmup

import kr.wooco.woocobe.common.warmup.Warmup
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Warmup 작업 실행 엔진
 *
 * 각 [Warmup] 컴포넌트의 iterations를 개별 작업으로 분해하여
 * 고정 크기 스레드풀에서 병렬 실행합니다.
 *
 * - 풀 크기는 총 작업 수와 [maxPoolSize] 중 작은 값으로 결정
 * - [awaitDuration] 내 완료되지 못한 작업은 취소 처리
 *
 * @property maxPoolSize 워커 스레드풀 최대 크기
 * @property awaitDuration 전체 warmup 최대 대기 시간
 */
internal class WarmupExecutor(
    private val maxPoolSize: Int,
    private val awaitDuration: Duration,
) {
    /**
     * Warmup 작업을 병렬 실행하고 결과를 반환합니다.
     *
     * @param targets 실행할 Warmup 컴포넌트 목록 (enabled 필터링 완료된 상태)
     * @return 각 컴포넌트별 성공/실패/미실행 수를 포함한 실행 결과
     */
    fun execute(targets: List<Warmup>): WarmupExecutionSummary {
        val totalTasks = targets.sumOf { it.iterations }
        val poolSize = minOf(totalTasks, maxPoolSize)
        val startTime = System.currentTimeMillis()

        val successCounts = ConcurrentHashMap<String, AtomicInteger>()
        val failedCounts = ConcurrentHashMap<String, AtomicInteger>()
        targets.forEach { warmup ->
            successCounts[warmup.name] = AtomicInteger(0)
            failedCounts[warmup.name] = AtomicInteger(0)
        }

        // 각 warmup의 iterations를 개별 Runnable로 분해
        val tasks = targets.flatMap { warmup ->
            List(warmup.iterations) {
                Runnable {
                    runCatching { warmup.execute() }
                        .onSuccess { successCounts[warmup.name]?.incrementAndGet() }
                        .onFailure { failedCounts[warmup.name]?.incrementAndGet() }
                }
            }
        }

        val executor = Executors.newFixedThreadPool(poolSize)
        val futures = tasks.map { executor.submit(it) }

        executor.shutdown()
        val completed = executor.awaitTermination(awaitDuration.seconds, TimeUnit.SECONDS)

        // timeout 시 미완료 작업 취소
        var cancelledCount = 0
        if (!completed) {
            val notDone = futures.filter { !it.isDone }
            notDone.forEach { it.cancel(true) }
            cancelledCount = notDone.size
            executor.shutdownNow()
        }

        val elapsed = System.currentTimeMillis() - startTime
        val results = targets.map { warmup ->
            WarmupResult(
                name = warmup.name,
                iterations = warmup.iterations,
                success = successCounts[warmup.name]?.get() ?: 0,
                failed = failedCounts[warmup.name]?.get() ?: 0,
            )
        }

        return WarmupExecutionSummary(
            results = results,
            elapsedMs = elapsed,
            timedOut = !completed,
            cancelledCount = cancelledCount,
        )
    }
}
