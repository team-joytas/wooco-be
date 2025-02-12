package kr.wooco.woocobe.redis.auth

import kr.wooco.woocobe.core.auth.domain.entity.AuthCode
import kr.wooco.woocobe.redis.auth.entity.AuthCodeRedisEntity
import org.springframework.stereotype.Component

@Component
internal class AuthCodePersistenceMapper {
    fun toDomain(authCodeRedisEntity: AuthCodeRedisEntity): AuthCode =
        AuthCode(
            id = authCodeRedisEntity.id,
            verifier = authCodeRedisEntity.verifier,
            challenge = authCodeRedisEntity.challenge,
        )

    fun toEntity(authCode: AuthCode): AuthCodeRedisEntity =
        AuthCodeRedisEntity(
            id = authCode.id,
            verifier = authCode.verifier,
            challenge = authCode.challenge,
        )
}
