package kr.wooco.woocobe.metric.warmup

/**
 * 전체 Warmup 실행의 종합 요약
 *
 * [WarmupExecutor.execute]의 반환값으로,
 * 모든 Warmup 컴포넌트의 결과와 실행 메타데이터를 포함합니다.
 *
 * @property results 각 Warmup 컴포넌트별 실행 결과
 * @property elapsedMs 전체 실행 소요 시간 (밀리초)
 * @property timedOut awaitDuration 내 완료되지 못한 경우 true
 * @property cancelledCount timeout으로 취소된 작업 수
 */
data class WarmupExecutionSummary(
    val results: List<WarmupResult>,
    val elapsedMs: Long,
    val timedOut: Boolean,
    val cancelledCount: Int = 0,
)
