package kr.wooco.woocobe.rest.auth

import kr.wooco.woocobe.core.user.application.port.out.dto.SocialUserInfo
import kr.wooco.woocobe.core.user.domain.vo.SocialProvider

interface SocialUserClient {
    fun supportProvider(provider: SocialProvider): Boolean

    fun fetchSocialUserInfo(code: String): SocialUserInfo

    fun revokeSocialUser(
        identifier: String,
        socialToken: String,
    )
}
