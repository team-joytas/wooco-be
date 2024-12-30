package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.infrastructure.client.KakaoOAuthRestClient
import org.springframework.stereotype.Component

@Component
internal class RestSocialAuthClientGateway(
    private val kakaoOAuthRestClient: KakaoOAuthRestClient,
) : SocialAuthClientGateway {
    override fun getSocialAuthInfo(socialToken: String): SocialAuth =
        kakaoOAuthRestClient
            .getKakaoOAuthInfo(socialToken)
            .toDomain()
}
