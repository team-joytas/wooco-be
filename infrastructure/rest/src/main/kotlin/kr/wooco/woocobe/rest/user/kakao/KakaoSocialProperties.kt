package kr.wooco.woocobe.rest.auth.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@ConfigurationProperties(prefix = "app.social.kakao")
data class KakaoSocialProperties(
    val clientId: String,
    val secretKey: String,
    val grantType: String,
    val redirectUri: String,
) {
    fun toSocialTokenRequestParams(code: String): MultiValueMap<String, String> =
        LinkedMultiValueMap<String, String>().apply {
            add("code", code)
            add("client_id", clientId)
            add("client_secret", secretKey)
            add("grant_type", grantType)
            add("redirect_uri", redirectUri)
        }

    fun toRefreshTokenRequestParams(refreshToken: String): MultiValueMap<String, String> =
        LinkedMultiValueMap<String, String>().apply {
            add("refresh_token", refreshToken)
            add("client_id", clientId)
            add("client_secret", secretKey)
            add("grant_type", "refresh_token")
        }
}
