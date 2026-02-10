package kr.wooco.woocobe.api.common.warmup

import kr.wooco.woocobe.common.warmup.AbstractWarmup
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

/**
 * Spring Security Filter Chain Warmup
 *
 * 내부 엔드포인트를 호출하여 Filter Chain을 warmup합니다.
 * 401/403 등 HTTP 에러는 무시합니다 (필터 체인은 실행됨).
 */
@Component
@EnableConfigurationProperties(FilterChainWarmupProperties::class)
class FilterChainWarmup(
    private val properties: FilterChainWarmupProperties,
) : AbstractWarmup() {
    override val name: String = NAME
    override val enabled: Boolean get() = properties.enabled
    override val iterations: Int get() = properties.iterations

    private val restClient: RestClient by lazy {
        RestClient
            .builder()
            .baseUrl("http://localhost:8080")
            .build()
    }

    override fun doWarmup() {
        restClient
            .get()
            .uri(PUBLIC_ENDPOINT)
            .retrieve()
            .toBodilessEntity()
    }

    companion object {
        const val NAME = "filter-chain"

        private const val PUBLIC_ENDPOINT = "/internal/warmup"
    }
}
