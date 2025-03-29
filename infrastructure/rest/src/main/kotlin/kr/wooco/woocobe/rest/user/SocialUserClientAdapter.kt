package kr.wooco.woocobe.rest.user

import kr.wooco.woocobe.core.user.application.port.out.SocialUserClientPort
import kr.wooco.woocobe.core.user.domain.vo.SocialType
import kr.wooco.woocobe.core.user.domain.vo.SocialUser
import org.springframework.stereotype.Component

@Component
internal class SocialUserClientAdapter(
    private val socialAuthClients: Set<SocialUserClient>,
) : SocialUserClientPort {
    override fun revokeSocialUser(
        socialToken: String,
        socialUser: SocialUser,
    ) {
        val socialAuthClient = convertSupportSocialClient(socialUser.socialType)
        socialAuthClient.revokeSocialUser(socialToken)
    }

    private fun convertSupportSocialClient(socialType: SocialType): SocialUserClient =
        socialAuthClients.firstOrNull { it.supportType(socialType) }
            ?: throw UnsupportedOperationException(NOT_IMPLEMENT_SOCIAL_CLIENT)

    companion object {
        private const val NOT_IMPLEMENT_SOCIAL_CLIENT = "구현되지 않은 소셜 클라이언트입니다."
    }
}
