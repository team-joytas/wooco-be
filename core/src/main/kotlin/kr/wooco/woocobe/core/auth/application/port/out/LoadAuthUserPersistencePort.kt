package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.AuthUser

interface LoadAuthUserPersistencePort {
    fun getOrNullBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): AuthUser?
}
