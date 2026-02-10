package kr.wooco.woocobe.mysql.common.warmup

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "warmup.mysql")
data class MysqlWarmupProperties(
    val enabled: Boolean,
    val iterations: Int,
)
