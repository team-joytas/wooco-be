package kr.wooco.woocobe.rest.auth

import kr.wooco.woocobe.core.user.application.port.out.SocialUserClientPort
import kr.wooco.woocobe.core.user.application.port.out.dto.SocialUserInfo
import kr.wooco.woocobe.core.user.domain.vo.SocialProvider
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import org.springframework.stereotype.Component

@Component
internal class SocialUserClientAdapter(
    private val socialAuthClients: Set<SocialUserClient>,
) : SocialUserClientPort {
    override fun fetchSocialUserInfo(
        code: String,
        provider: SocialProvider,
    ): SocialUserInfo {
        val socialAuthClient = convertSupportSocialClient(provider)
        return socialAuthClient.fetchSocialUserInfo(code)
    }

    override fun revokeSocialUser(socialUser: SocialUser) {
        val socialAuthClient = convertSupportSocialClient(socialUser.provider)
        socialAuthClient.revokeSocialUser(socialUser.identifier, socialUser.socialToken)
    }

    private fun convertSupportSocialClient(provider: SocialProvider): SocialUserClient =
        socialAuthClients.firstOrNull { it.supportProvider(provider) }
            ?: throw UnsupportedOperationException(NOT_IMPLEMENT_SOCIAL_CLIENT)

    companion object {
        private const val NOT_IMPLEMENT_SOCIAL_CLIENT = "구현되지 않은 소셜 클라이언트입니다."
    }
}
