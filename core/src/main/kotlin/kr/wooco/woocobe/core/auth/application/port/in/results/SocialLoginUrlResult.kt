package kr.wooco.woocobe.core.auth.application.port.`in`.results

data class SocialLoginUrlResult(
    val loginUrl: String,
    val authCodeId: String,
)
