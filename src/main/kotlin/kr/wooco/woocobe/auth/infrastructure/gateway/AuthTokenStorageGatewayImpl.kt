package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.exception.NotExistsTokenException
import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.auth.infrastructure.cache.AuthTokenCacheMapper
import kr.wooco.woocobe.auth.infrastructure.cache.repository.AuthTokenRedisRepository
import org.springframework.stereotype.Component

@Component
internal class AuthTokenStorageGatewayImpl(
    private val authTokenRedisRepository: AuthTokenRedisRepository,
    private val authTokenCacheMapper: AuthTokenCacheMapper,
) : AuthTokenStorageGateway {
    override fun save(authToken: AuthToken): AuthToken {
        val authTokenEntity = authTokenCacheMapper.toEntity(authToken)
        authTokenRedisRepository.save(authTokenEntity)
        return authTokenCacheMapper.toDomain(authTokenEntity)
    }

    override fun getWithDeleteByTokenId(tokenId: String): AuthToken {
        val authTokenEntity = authTokenRedisRepository.findAndDeleteById(tokenId)
            ?: throw NotExistsTokenException
        return authTokenCacheMapper.toDomain(authTokenEntity)
    }

    override fun deleteByTokenId(tokenId: String) {
        authTokenRedisRepository.deleteById(tokenId)
    }
}
