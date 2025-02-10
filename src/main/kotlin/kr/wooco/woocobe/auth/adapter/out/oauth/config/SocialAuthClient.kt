package kr.wooco.woocobe.auth.adapter.out.oauth.config

interface SocialAuthClient {
    fun isSupportSocialType(socialType: String): Boolean

    fun fetchSocialAuth(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): SocialAuthResponse

    fun generateSocialLoginUrl(codeChallenge: String): String
}
