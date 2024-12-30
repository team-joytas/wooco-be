package kr.wooco.woocobe.auth.domain.model

class SocialAuth(
    val socialId: String,
    val socialType: SocialType,
) {
    companion object {
        fun register(
            socialId: String,
            socialType: String,
        ): SocialAuth =
            SocialAuth(
                socialId = socialId,
                socialType = SocialType.from(socialType),
            )
    }
}
