package kr.wooco.woocobe.auth.infrastructure.client.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@ConfigurationProperties(prefix = "app.social.kakao")
data class KakaoSocialAuthProperties(
    val clientId: String,
    val secretKey: String,
    val grantType: String,
    val redirectUri: String,
) {
    fun toLoginUrlParams(codeChallenge: String): MultiValueMap<String, String> =
        LinkedMultiValueMap<String, String>().apply {
            add("code_challenge", codeChallenge)
            add("code_challenge_method", "S256")
            add("response_type", "code")
            add("client_id", clientId)
            add("redirect_uri", redirectUri)
        }

    fun toTokenRequestParams(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): MultiValueMap<String, String> =
        LinkedMultiValueMap<String, String>().apply {
            add("code", code)
            add("code_verifier", codeVerifier)
            add("code_challenge", codeChallenge)
            add("code_challenge_method", "S256")
            add("client_id", clientId)
            add("client_secret", secretKey)
            add("grant_type", grantType)
            add("redirect_uri", redirectUri)
        }
}
