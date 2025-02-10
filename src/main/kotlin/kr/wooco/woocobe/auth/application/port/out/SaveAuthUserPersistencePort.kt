package kr.wooco.woocobe.auth.application.port.out

import kr.wooco.woocobe.auth.domain.entity.AuthUser

interface SaveAuthUserPersistencePort {
    fun saveAuthUser(authUser: AuthUser): AuthUser
}
