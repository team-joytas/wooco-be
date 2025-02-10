package kr.wooco.woocobe.auth.adapter.out.persistence

import kr.wooco.woocobe.auth.adapter.out.persistence.repository.AuthCodeRedisRepository
import kr.wooco.woocobe.auth.application.port.out.AuthCodePersistencePort
import kr.wooco.woocobe.auth.domain.entity.AuthCode
import kr.wooco.woocobe.auth.domain.exception.NotExistsAuthCodeException
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
