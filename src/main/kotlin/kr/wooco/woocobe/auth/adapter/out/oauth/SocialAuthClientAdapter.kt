package kr.wooco.woocobe.auth.adapter.out.oauth

import kr.wooco.woocobe.auth.adapter.out.oauth.config.SocialAuthClient
import kr.wooco.woocobe.auth.application.port.out.SocialAuthClientPort
import kr.wooco.woocobe.auth.domain.vo.SocialInfo
import org.springframework.stereotype.Component

@Component
internal class SocialAuthClientAdapter(
    private val socialAuthClients: Set<SocialAuthClient>,
) : SocialAuthClientPort {
    override fun fetchSocialInfo(
        code: String,
        socialType: String,
        verifier: String,
        challenge: String,
    ): SocialInfo {
        val socialAuthClient = socialAuthClients.firstOrNull { it.isSupportSocialType(socialType) }
            ?: throw UnsupportedOperationException(CLIENT_NOT_FOUND_MESSAGE)
        return socialAuthClient.fetchSocialAuth(code, verifier, challenge).toDomain()
    }

    override fun getSocialLoginUrl(
        socialType: String,
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
