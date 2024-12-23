package kr.wooco.woocobe.auth.domain.model

class AuthUser(
    val id: Long,
    val userId: Long,
    val socialAuthInfo: SocialAuthInfo,
) {
    companion object {
        fun register(
            userId: Long = 0L,
            socialAuthInfo: SocialAuthInfo,
        ): AuthUser =
            AuthUser(
                id = 0L,
                userId = userId,
                socialAuthInfo = socialAuthInfo,
            )
    }
}
