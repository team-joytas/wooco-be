package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.common.domain.IdGenerator

// TODO: 네이밍 일관화 필요 -> ex) AuthTokenRedisEntity, AuthTokenJpaEntity
class AuthTokenEntity(
    val id: Long,
    val userId: Long,
) {
    fun toDomain(): AuthToken =
        AuthToken(
            id = id,
            userId = userId,
        )

    companion object {
        fun from(authToken: AuthToken): AuthTokenEntity =
            AuthTokenEntity(
                id = IdGenerator.generateId(),
                userId = authToken.userId,
            )
    }
}
