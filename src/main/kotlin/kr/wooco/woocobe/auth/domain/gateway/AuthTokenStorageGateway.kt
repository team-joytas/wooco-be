package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.AuthToken

interface AuthTokenStorageGateway {
    fun save(authToken: AuthToken): AuthToken

    fun getWithDeleteByTokenId(tokenId: String): AuthToken

    fun deleteByTokenId(tokenId: String)
}
