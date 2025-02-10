package kr.wooco.woocobe.auth.adapter.out.persistence

import kr.wooco.woocobe.auth.adapter.out.persistence.entity.AuthTokenRedisEntity
import kr.wooco.woocobe.auth.domain.entity.AuthToken
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
