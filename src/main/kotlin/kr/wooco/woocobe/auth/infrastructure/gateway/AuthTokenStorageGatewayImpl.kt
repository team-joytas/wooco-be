package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.auth.infrastructure.storage.AuthTokenEntity
import kr.wooco.woocobe.auth.infrastructure.storage.AuthTokenRedisRepository
import org.springframework.stereotype.Component

@Component
internal class AuthTokenStorageGatewayImpl(
    private val authTokenRedisRepository: AuthTokenRedisRepository,
) : AuthTokenStorageGateway {
    override fun save(authToken: AuthToken): AuthToken = authTokenRedisRepository.save(AuthTokenEntity.from(authToken)).toDomain()

    override fun getByTokenId(tokenId: Long): AuthToken? = authTokenRedisRepository.findById(tokenId)?.toDomain()

    override fun getWithDeleteByTokenId(tokenId: Long): AuthToken? = authTokenRedisRepository.findAndDeleteById(tokenId)?.toDomain()

    override fun deleteByTokenId(tokenId: Long) = authTokenRedisRepository.deleteById(tokenId)
}
