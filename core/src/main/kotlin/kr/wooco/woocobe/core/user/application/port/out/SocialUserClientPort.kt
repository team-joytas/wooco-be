package kr.wooco.woocobe.core.user.application.port.out

import kr.wooco.woocobe.core.user.domain.vo.SocialUser

interface SocialUserClientPort {
    fun revokeSocialUser(
        socialToken: String,
        socialUser: SocialUser,
    )
}
