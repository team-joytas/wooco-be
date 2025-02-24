package kr.wooco.woocobe.core.auth.application.port.out

import kr.wooco.woocobe.core.auth.domain.entity.AuthToken

interface AuthTokenPersistencePort {
    fun getWithDeleteByTokenId(tokenId: String): AuthToken

    fun saveAuthToken(authToken: AuthToken): AuthToken

    fun deleteByTokenId(tokenId: String)
}
