package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialType

interface AuthUserStorageGateway {
    fun save(authUser: AuthUser): AuthUser

    fun getOrNullBySocialIdAndSocialType(
        socialId: String,
        socialType: SocialType,
    ): AuthUser?

    fun deleteByUserId(userId: Long)
}
