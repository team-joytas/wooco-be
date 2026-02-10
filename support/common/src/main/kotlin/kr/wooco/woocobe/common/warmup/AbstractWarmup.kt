package kr.wooco.woocobe.common.warmup

/**
 * Warmup 기본 구현체
 *
 * 단일 warmup 작업을 정의합니다.
 * 반복 실행 및 스레드 관리는 WarmupSupport가 담당합니다.
 */
abstract class AbstractWarmup : Warmup {
    /**
     * 단일 warmup 작업 실행
     *
     * 여러 스레드에서 동시에 호출될 수 있으므로 thread-safe 하게 구현해야 합니다.
     */
    override fun execute() {
        doWarmup()
    }

    /**
     * 실제 warmup 로직 구현
     */
    protected abstract fun doWarmup()
}
