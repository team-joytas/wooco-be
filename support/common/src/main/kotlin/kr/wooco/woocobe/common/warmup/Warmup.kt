package kr.wooco.woocobe.common.warmup

/**
 * Warmup 인터페이스
 *
 * 각 모듈에서 warmup이 필요한 컴포넌트는 이 인터페이스를 구현합니다.
 */
interface Warmup {
    /** Warmup 컴포넌트의 고유 이름 */
    val name: String

    /** Warmup 활성화 여부 */
    val enabled: Boolean
        get() = true

    /** Warmup 반복 횟수 */
    val iterations: Int

    /** 단일 warmup 작업 실행 (thread-safe 해야 함) */
    fun execute()
}
