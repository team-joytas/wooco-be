package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.AuthUser
import kr.wooco.woocobe.auth.domain.model.SocialAuthType

interface AuthUserStorageGateway {
    fun save(authUser: AuthUser): AuthUser

    fun getByAuthUserId(authUserId: Long): AuthUser?

    fun getBySocialIdAndSocialType(
        socialId: String,
        socialType: SocialAuthType,
    ): AuthUser?

    fun deleteByUserId(userId: Long)
}
