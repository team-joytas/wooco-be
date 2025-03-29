package kr.wooco.woocobe.rest.user

import kr.wooco.woocobe.core.user.domain.vo.SocialType

interface SocialUserClient {
    fun supportType(socialType: SocialType): Boolean

    fun revokeSocialUser(accessToken: String)
}
