package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.AuthUser

interface SaveAuthUserPersistencePort {
    fun saveAuthUser(authUser: AuthUser): AuthUser
}
