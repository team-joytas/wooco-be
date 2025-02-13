package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.vo.SocialInfo

interface SocialAuthClientPort {
    fun fetchSocialInfo(
        code: String,
        socialType: String,
        verifier: String,
        challenge: String,
    ): SocialInfo

    fun getSocialLoginUrl(
        socialType: String,
        challenge: String,
    ): String
}
