package kr.wooco.woocobe.metric.warmup

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

/**
 * Warmup 전역 설정
 *
 * application-metric.yml에서 warmup.* 설정을 바인딩합니다.
 *
 * 예시:
 * ```yaml
 * warmup:
 *   enabled: true
 *   max-pool-size: 5
 *   await-duration: 60s
 * ```
 */
@ConfigurationProperties(prefix = "warmup")
data class WarmupProperties(
    val enabled: Boolean = true,
    val maxPoolSize: Int,
    val awaitDuration: Duration,
)
