package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.model.Pkce
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthClient
import org.springframework.stereotype.Component

@Component
internal class SocialAuthClientGatewayImpl(
    private val socialAuthClients: Set<SocialAuthClient>,
) : SocialAuthClientGateway {
    override fun fetchSocialAuth(
        authCode: String,
        socialType: SocialType,
        pkce: Pkce,
    ): SocialAuth {
        val socialAuthClient = socialAuthClients.firstOrNull { it.isSupportSocialType(socialType) }
            ?: throw UnsupportedOperationException(CLIENT_NOT_FOUND_MESSAGE)
        return socialAuthClient.fetchSocialAuth(authCode, pkce.verifier, pkce.challenge).toDomain()
    }

    override fun getSocialLoginUrl(
        socialType: SocialType,
        challenge: String,
    ): String {
        val socialAuthClient = socialAuthClients.firstOrNull { it.isSupportSocialType(socialType) }
            ?: throw UnsupportedOperationException(CLIENT_NOT_FOUND_MESSAGE)
        return socialAuthClient.generateSocialLoginUrl(challenge)
    }

    companion object {
        private const val CLIENT_NOT_FOUND_MESSAGE = "미구현된 소셜 인증 클라이언트 타입입니다."
    }
}
