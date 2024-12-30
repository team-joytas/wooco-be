package kr.wooco.woocobe.auth.infrastructure.client.kakao

import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthClient
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthResponse
import org.springframework.stereotype.Component

@Component
class KakaoSocialAuthClient(
    private val kakaoSocialAuthApiClient: KakaoSocialAuthApiClient,
    private val kakaoSocialAuthProperties: KakaoSocialAuthProperties,
) : SocialAuthClient {
    override fun isSupportSocialType(socialType: SocialType): Boolean = socialType == SocialType.KAKAO

    override fun fetchSocialAuth(code: String): SocialAuthResponse {
        val tokenRequestParams = kakaoSocialAuthProperties.toTokenRequestParams(code)
        val tokenResponse = kakaoSocialAuthApiClient.fetchSocialToken(tokenRequestParams)
        return kakaoSocialAuthApiClient.fetchSocialAuth(tokenResponse.accessToken)
    }
}
