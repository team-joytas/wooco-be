package kr.wooco.woocobe.auth.application.port.out

import kr.wooco.woocobe.auth.domain.entity.AuthUser

interface LoadAuthUserPersistencePort {
    fun getOrNullBySocialIdAndSocialType(
        socialId: String,
        socialType: String,
    ): AuthUser?
}
