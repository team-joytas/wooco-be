package kr.wooco.woocobe.redis.auth

import kr.wooco.woocobe.core.auth.application.port.out.AuthCodePersistencePort
import kr.wooco.woocobe.core.auth.domain.entity.AuthCode
import kr.wooco.woocobe.core.auth.domain.exception.NotExistsAuthCodeException
import kr.wooco.woocobe.redis.auth.repository.AuthCodeRedisRepository
import org.springframework.stereotype.Component

@Component
internal class AuthCodePersistenceAdapter(
    private val authCodeRedisRepository: AuthCodeRedisRepository,
    private val authCodePersistenceMapper: AuthCodePersistenceMapper,
) : AuthCodePersistencePort {
    override fun saveAuthCode(authCode: AuthCode): AuthCode {
        val authCodeRedisEntity = authCodePersistenceMapper.toEntity(authCode)
        authCodeRedisRepository.save(authCodeRedisEntity)
        return authCode
    }

    override fun getWithDeleteByAuthCodeId(authCodeId: String): AuthCode {
        val authCode = authCodeRedisRepository.findAndDeleteByAuthCodeId(authCodeId)
            ?: throw NotExistsAuthCodeException
        return authCodePersistenceMapper.toDomain(authCode)
    }
}
