package kr.wooco.woocobe.auth.adapter.`in`.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.cors")
data class CorsProperties(
    val maxAge: Long,
    val allowedMethods: List<String>,
    val allowedOrigins: List<String>,
    val allowedHeaders: List<String>,
    val exposedHeaders: List<String>,
    val allowCredentials: Boolean,
)
