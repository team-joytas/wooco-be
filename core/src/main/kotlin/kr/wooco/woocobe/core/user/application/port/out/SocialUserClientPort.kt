package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.user.application.port.out.dto.SocialUserInfo
import kr.wooco.woocobe.core.user.domain.vo.SocialProvider
import kr.wooco.woocobe.core.user.domain.vo.SocialUser

interface SocialAuthClientPort {
    fun fetchSocialUserInfo(
        code: String,
        provider: SocialProvider,
    ): SocialUserInfo

    fun revokeSocialUser(socialUser: SocialUser)
}
