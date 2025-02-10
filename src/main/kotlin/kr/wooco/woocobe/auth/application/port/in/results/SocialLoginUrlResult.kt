package kr.wooco.woocobe.auth.application.port.`in`.results

data class SocialLoginUrlResult(
    val loginUrl: String,
    val authCodeId: String,
)
