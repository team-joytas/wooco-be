package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType

interface SocialAuthClientGateway {
    fun fetchSocialAuth(
        authCode: String,
        socialType: SocialType,
    ): SocialAuth
}
