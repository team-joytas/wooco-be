package kr.wooco.woocobe.auth.application.port.out

import kr.wooco.woocobe.auth.domain.entity.AuthToken

interface AuthTokenPersistencePort {
    fun getWithDeleteByTokenId(tokenId: String): AuthToken

    fun saveAuthToken(authToken: AuthToken): AuthToken

    fun deleteByTokenId(tokenId: String)
}
