package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.model.SocialAuthInfo
import kr.wooco.woocobe.auth.infrastructure.client.KakaoOAuthRestClient
import org.springframework.stereotype.Component

@Component
class RestSocialAuthClientGateway(
    private val kakaoOAuthRestClient: KakaoOAuthRestClient,
) : SocialAuthClientGateway {
    override fun getSocialAuthInfo(socialToken: String): SocialAuthInfo =
        kakaoOAuthRestClient
            .getKakaoOAuthInfo(socialToken)
            .toDomain()
}
