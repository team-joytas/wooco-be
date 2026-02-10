package kr.wooco.woocobe.metric.warmup

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.common.warmup.Warmup
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicBoolean

private val logger = KotlinLogging.logger {}

/**
 * Warmup 오케스트레이터
 *
 * 애플리케이션 시작 시 등록된 [Warmup] 컴포넌트들을 [WarmupExecutor]를 통해 실행하고,
 * Readiness Probe로 warmup 완료 여부를 노출합니다.
 *
 * - [AbstractHealthIndicator]를 상속하여 `/actuator/health/readiness`에 warmup 상태를 반영
 * - warmup 완료 전: DOWN → K8s가 트래픽을 보내지 않음
 * - warmup 완료 후 또는 비활성화 시: UP
 *
 * `@ConditionalOnProperty` 대신 런타임 프로퍼티로 활성화를 제어합니다.
 * Spring AOT 환경에서 `@ConditionalOnProperty`는 빌드 시 고정되어
 * 런타임에 동적으로 변경할 수 없기 때문입니다.
 *
 * @see WarmupExecutor 실제 warmup 작업 병렬 실행 엔진
 * @see WarmupProperties warmup 전역 설정
 */
@Component
@EnableConfigurationProperties(WarmupProperties::class)
internal class WarmupSupport(
    private val warmups: List<Warmup>,
    private val properties: WarmupProperties,
) : AbstractHealthIndicator(),
    ApplicationListener<ApplicationReadyEvent> {
    private val isDone = AtomicBoolean(false)

    override fun doHealthCheck(builder: Health.Builder) {
        if (!properties.enabled || isDone.get()) {
            builder.up()
        } else {
            builder.down()
        }
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        if (!properties.enabled) {
            logger.info { "Warmup is disabled, skipping" }
            isDone.set(true)
            return
        }

        val targets = warmups.filter { it.enabled }
        if (targets.isNotEmpty()) {
            logger.info {
                val summary = targets.joinToString { "${it.name}=${it.iterations}" }
                "Warmup starting: ${targets.sumOf { it.iterations }} tasks ($summary)"
            }
            val summary = WarmupExecutor(properties.maxPoolSize, properties.awaitDuration).execute(targets)
            logResult(summary)
        }

        isDone.set(true)
    }

    private fun logResult(summary: WarmupExecutionSummary) {
        if (summary.timedOut) {
            logger.warn {
                "Warmup timed out after ${summary.elapsedMs}ms " +
                    "(limit=${properties.awaitDuration.seconds}s), " +
                    "${summary.cancelledCount} tasks cancelled"
            }
        }

        summary.results.forEach { warmupResult ->
            if (warmupResult.hasFailure) {
                logger.warn { warmupResult.toLogMessage() }
            } else {
                logger.info { warmupResult.toLogMessage() }
            }
        }

        if (!summary.timedOut) {
            logger.info { "Warmup completed in ${summary.elapsedMs}ms" }
        }
    }
}
