package kr.wooco.woocobe.auth.domain.model

class AuthUser(
    val id: Long,
    val userId: Long,
    val socialAuth: SocialAuth,
) {
    companion object {
        fun register(
            userId: Long = 0L,
            socialAuth: SocialAuth,
        ): AuthUser =
            AuthUser(
                id = 0L,
                userId = userId,
                socialAuth = socialAuth,
            )
    }
}
