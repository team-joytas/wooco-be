package kr.wooco.woocobe.api.common.warmup

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "warmup.filter-chain")
data class FilterChainWarmupProperties(
    val enabled: Boolean = true,
    val iterations: Int = 50,
)
