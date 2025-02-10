package kr.wooco.woocobe.auth.adapter.out.persistence

import kr.wooco.woocobe.auth.adapter.out.persistence.entity.AuthCodeRedisEntity
import kr.wooco.woocobe.auth.domain.entity.AuthCode
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
