package kr.wooco.woocobe.rest.auth.kakao

import kr.wooco.woocobe.core.user.application.port.out.dto.SocialUserInfo
import kr.wooco.woocobe.core.user.domain.vo.SocialProvider
import kr.wooco.woocobe.rest.auth.SocialUserClient
import org.springframework.stereotype.Component

@Component
class KakaoSocialUserClient(
    private val kakaoSocialApiClient: KakaoSocialApiClient,
    private val kakaoSocialProperties: KakaoSocialProperties,
) : SocialUserClient {
    override fun supportProvider(provider: SocialProvider): Boolean = provider == SocialProvider.KAKAO

    override fun fetchSocialUserInfo(code: String): SocialUserInfo {
        val socialTokenRequestParams = kakaoSocialProperties.toSocialTokenRequestParams(code)
        val socialTokenResponse = kakaoSocialApiClient.fetchSocialToken(socialTokenRequestParams)
        val bearerHeader = createBearerHeader(socialTokenResponse.accessToken)
        val socialUserResponse = kakaoSocialApiClient.fetchSocialUser(bearerHeader)
        return socialUserResponse.toDto(socialTokenResponse.refreshToken!!)
    }

    override fun revokeSocialUser(
        identifier: String,
        socialToken: String,
    ) {
        val refreshTokenRequestParams = kakaoSocialProperties.toRefreshTokenRequestParams(socialToken)
        val socialTokenResponse = kakaoSocialApiClient.fetchSocialToken(refreshTokenRequestParams)
        val bearerHeader = createBearerHeader(socialTokenResponse.accessToken)
        kakaoSocialApiClient.revokeSocialUser(bearerHeader)
    }

    companion object {
        private fun createBearerHeader(accessToken: String) = "Bearer $accessToken"
    }
}
