package kr.wooco.woocobe.metric.warmup

/**
 * 개별 Warmup 컴포넌트의 실행 결과
 *
 * @property name warmup 컴포넌트 이름 (예: "mysql", "redis")
 * @property iterations 요청된 총 반복 횟수
 * @property success 성공한 반복 횟수
 * @property failed 실패한 반복 횟수
 */
data class WarmupResult(
    val name: String,
    val iterations: Int,
    val success: Int,
    val failed: Int,
) {
    /** timeout으로 인해 실행되지 못한 반복 횟수 */
    val notExecuted: Int get() = iterations - success - failed

    /** 실패 또는 미실행 작업이 존재하는지 여부 */
    val hasFailure: Boolean get() = failed > 0 || notExecuted > 0

    /** 로그 출력용 요약 메시지 */
    fun toLogMessage(): String {
        val detail = listOfNotNull(
            "success=$success",
            "failed=$failed",
            if (notExecuted > 0) "not-executed=$notExecuted" else null,
        ).joinToString()
        return "[WARMUP] $name: $detail (total=$iterations)"
    }
}
