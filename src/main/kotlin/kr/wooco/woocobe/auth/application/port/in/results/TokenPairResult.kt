package kr.wooco.woocobe.auth.application.port.`in`.results

data class TokenPairResult(
    val accessToken: String,
    val refreshToken: String,
)
