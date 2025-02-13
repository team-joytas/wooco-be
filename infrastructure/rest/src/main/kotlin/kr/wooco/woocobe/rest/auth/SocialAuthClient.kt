package kr.wooco.woocobe.rest.auth

interface SocialAuthClient {
    fun isSupportSocialType(socialType: String): Boolean

    fun fetchSocialAuth(
        code: String,
        codeVerifier: String,
        codeChallenge: String,
    ): SocialAuthResponse

    fun generateSocialLoginUrl(codeChallenge: String): String
}
