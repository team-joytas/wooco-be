package kr.wooco.woocobe.auth.infrastructure.client.kakao

import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthClient
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthResponse
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoSocialAuthClient(
    private val kakaoSocialAuthApiClient: KakaoSocialAuthApiClient,
    private val kakaoSocialAuthProperties: KakaoSocialAuthProperties,
) : SocialAuthClient {
    override fun isSupportSocialType(socialType: SocialType): Boolean = socialType == SocialType.KAKAO

    override fun fetchSocialAuth(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): SocialAuthResponse {
        val tokenRequestParams = kakaoSocialAuthProperties.toTokenRequestParams(code, codeVerifier, codeChallenge)
        val tokenResponse = kakaoSocialAuthApiClient.fetchSocialToken(tokenRequestParams)
        return kakaoSocialAuthApiClient.fetchSocialAuth("Bearer ${tokenResponse.accessToken}")
    }

    override fun generateSocialLoginUrl(codeChallenge: String): String =
        UriComponentsBuilder
            .fromHttpUrl(KAKAO_AUTH_URL)
            .queryParams(kakaoSocialAuthProperties.toLoginUrlParams(codeChallenge))
            .build()
            .toUriString()

    companion object {
        private const val KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize"
    }
}
