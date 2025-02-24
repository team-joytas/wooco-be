package kr.wooco.woocobe.core.auth.application.port.`in`.results

data class TokenPairResult(
    val accessToken: String,
    val refreshToken: String,
)
