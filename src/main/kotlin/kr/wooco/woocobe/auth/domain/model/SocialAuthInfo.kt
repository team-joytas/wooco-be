package kr.wooco.woocobe.auth.domain.model

class SocialAuthInfo(
    val socialId: String,
    val socialType: SocialAuthType,
) {
    companion object {
        fun register(
            socialId: String,
            socialType: String,
        ): SocialAuthInfo =
            SocialAuthInfo(
                socialId = socialId,
                socialType = SocialAuthType.from(socialType),
            )
    }
}
