package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.SocialAuthClientGateway
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthClient
import org.springframework.stereotype.Component

@Component
internal class RestSocialAuthClientGateway(
    private val socialAuthClients: Set<SocialAuthClient>,
) : SocialAuthClientGateway {
    override fun fetchSocialAuth(
        authCode: String,
        socialType: SocialType,
    ): SocialAuth {
        val socialAuthClient = socialAuthClients.firstOrNull { it.isSupportSocialType(socialType) }
            ?: throw RuntimeException("un supported social type")
        return socialAuthClient.fetchSocialAuth(authCode).toDomain()
    }
}
