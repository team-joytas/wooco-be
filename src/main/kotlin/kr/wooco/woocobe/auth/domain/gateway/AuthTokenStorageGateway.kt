package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.AuthToken

interface AuthTokenStorageGateway {
    fun save(authToken: AuthToken): AuthToken

    fun getByTokenId(tokenId: Long): AuthToken?

    fun getWithDeleteByTokenId(tokenId: Long): AuthToken?

    fun deleteByTokenId(tokenId: Long)
}
