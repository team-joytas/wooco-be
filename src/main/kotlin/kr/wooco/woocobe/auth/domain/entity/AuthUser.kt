package kr.wooco.woocobe.auth.domain.entity

import kr.wooco.woocobe.auth.domain.vo.SocialInfo

data class AuthUser(
    val id: Long,
    val userId: Long,
    val socialInfo: SocialInfo,
) {
    companion object {
        fun create(
            userId: Long,
            socialInfo: SocialInfo,
        ): AuthUser =
            AuthUser(
                id = 0L,
                userId = userId,
                socialInfo = socialInfo,
            )
    }
}
