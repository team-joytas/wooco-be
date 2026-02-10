package kr.wooco.woocobe.redis.common.warmup

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "warmup.redis")
data class RedisWarmupProperties(
    val enabled: Boolean = true,
    val iterations: Int = 100,
)
