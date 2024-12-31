package kr.wooco.woocobe.auth.infrastructure.client

import kr.wooco.woocobe.auth.domain.model.SocialType

interface SocialAuthClient {
    fun isSupportSocialType(socialType: SocialType): Boolean

    fun fetchSocialAuth(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): SocialAuthResponse

    fun generateSocialLoginUrl(codeChallenge: String): String
}
