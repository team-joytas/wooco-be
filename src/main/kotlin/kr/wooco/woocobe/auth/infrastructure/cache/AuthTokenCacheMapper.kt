package kr.wooco.woocobe.auth.infrastructure.cache

import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.auth.infrastructure.cache.entity.AuthTokenRedisEntity
import org.springframework.stereotype.Component

@Component
class AuthTokenCacheMapper {
    fun toDomain(authTokenEntity: AuthTokenRedisEntity): AuthToken =
        AuthToken(
            userId = authTokenEntity.userId,
            tokenId = authTokenEntity.tokenId,
        )

    fun toEntity(authToken: AuthToken): AuthTokenRedisEntity =
        AuthTokenRedisEntity(
            userId = authToken.userId,
            tokenId = authToken.tokenId,
        )
}
