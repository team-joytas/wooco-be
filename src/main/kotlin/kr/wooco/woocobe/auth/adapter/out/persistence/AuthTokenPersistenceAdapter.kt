package kr.wooco.woocobe.auth.adapter.out.persistence

import kr.wooco.woocobe.auth.adapter.out.persistence.repository.AuthTokenRedisRepository
import kr.wooco.woocobe.auth.application.port.out.AuthTokenPersistencePort
import kr.wooco.woocobe.auth.domain.entity.AuthToken
import kr.wooco.woocobe.auth.domain.exception.NotExistsTokenException
import org.springframework.stereotype.Component

@Component
internal class AuthTokenPersistenceAdapter(
    private val authTokenRedisRepository: AuthTokenRedisRepository,
    private val authTokenPersistenceMapper: AuthTokenPersistenceMapper,
) : AuthTokenPersistencePort {
    override fun getWithDeleteByTokenId(tokenId: String): AuthToken {
        val authTokenEntity = authTokenRedisRepository.findAndDeleteById(tokenId)
            ?: throw NotExistsTokenException
        return authTokenPersistenceMapper.toDomain(authTokenEntity)
    }

    override fun saveAuthToken(authToken: AuthToken): AuthToken {
        val authTokenEntity = authTokenPersistenceMapper.toEntity(authToken)
        authTokenRedisRepository.save(authTokenEntity)
        return authTokenPersistenceMapper.toDomain(authTokenEntity)
    }

    override fun deleteByTokenId(tokenId: String) {
        authTokenRedisRepository.deleteById(tokenId)
    }
}
