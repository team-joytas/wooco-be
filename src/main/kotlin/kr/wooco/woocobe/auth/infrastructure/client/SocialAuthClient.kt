package kr.wooco.woocobe.auth.infrastructure.client

import kr.wooco.woocobe.auth.domain.model.SocialType

interface SocialAuthClient {
    fun isSupportSocialType(socialType: SocialType): Boolean

    fun fetchSocialAuth(code: String): SocialAuthResponse
}
