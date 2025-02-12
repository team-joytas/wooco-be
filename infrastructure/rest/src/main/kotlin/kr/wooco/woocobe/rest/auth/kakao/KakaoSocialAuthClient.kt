package kr.wooco.woocobe.rest.auth.kakao

import kr.wooco.woocobe.rest.auth.SocialAuthClient
import kr.wooco.woocobe.rest.auth.SocialAuthResponse
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoSocialAuthClient(
    private val kakaoSocialAuthApiClient: KakaoSocialAuthApiClient,
    private val kakaoSocialAuthProperties: KakaoSocialAuthProperties,
) : SocialAuthClient {
    override fun isSupportSocialType(socialType: String): Boolean = socialType.equals(SUPPORT_TYPE, ignoreCase = true)

    override fun fetchSocialAuth(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): SocialAuthResponse {
        val tokenRequestParams = kakaoSocialAuthProperties.toTokenRequestParams(code, codeVerifier, codeChallenge)
        val tokenResponse = kakaoSocialAuthApiClient.fetchSocialToken(tokenRequestParams)
        val bearerHeader = createBearerHeader(tokenResponse.accessToken)
        return kakaoSocialAuthApiClient.fetchSocialAuth(bearerHeader)
    }

    override fun generateSocialLoginUrl(codeChallenge: String): String =
        UriComponentsBuilder
            .fromHttpUrl(KAKAO_AUTH_URL)
            .queryParams(kakaoSocialAuthProperties.toLoginUrlParams(codeChallenge))
            .build()
            .toUriString()

    companion object {
        private const val SUPPORT_TYPE = "KAKAO"
        private const val KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize"

        private fun createBearerHeader(accessToken: String) = "Bearer $accessToken"
    }
}
