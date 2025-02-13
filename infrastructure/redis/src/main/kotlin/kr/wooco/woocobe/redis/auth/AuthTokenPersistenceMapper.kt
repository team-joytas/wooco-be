package kr.wooco.woocobe.redis.auth

import kr.wooco.woocobe.core.auth.domain.entity.AuthToken
import kr.wooco.woocobe.redis.auth.entity.AuthTokenRedisEntity
import org.springframework.stereotype.Component

@Component
internal class AuthTokenPersistenceMapper {
    fun toDomain(authTokenEntity: AuthTokenRedisEntity): AuthToken =
        AuthToken(
            id = authTokenEntity.id,
            userId = authTokenEntity.userId,
        )

    fun toEntity(authToken: AuthToken): AuthTokenRedisEntity =
        AuthTokenRedisEntity(
            id = authToken.id,
            userId = authToken.userId,
        )
}
