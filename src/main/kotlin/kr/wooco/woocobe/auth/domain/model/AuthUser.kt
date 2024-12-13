package kr.wooco.woocobe.auth.domain.model

import kr.wooco.woocobe.common.domain.IdGenerator

class AuthUser(
    val id: Long,
    val userId: Long = 0L,
    val socialId: String,
    val socialType: SocialAuthType,
) {
    companion object {
        fun register(
            socialId: String,
            socialType: SocialAuthType,
        ): AuthUser =
            AuthUser(
                id = IdGenerator.generateId(),
                userId = IdGenerator.generateId(),
                socialId = socialId,
                socialType = socialType,
            )
    }
}
