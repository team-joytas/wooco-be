package kr.wooco.woocobe.auth.infrastructure.client.kakao

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.social.kakao")
data class KakaoSocialAuthProperties(
    val clientId: String,
    val secretKey: String,
    val grantType: String,
    val redirectUri: String,
) {
    fun toTokenRequestParams(code: String): Map<String, String> =
        mapOf(
            "code" to code,
            "client_id" to clientId,
            "client_secret" to secretKey,
            "grant_type" to grantType,
            "redirect_uri" to redirectUri,
        )
}
