package kr.wooco.woocobe.auth.infrastructure.cache

import kr.wooco.woocobe.auth.domain.model.Pkce
import kr.wooco.woocobe.auth.infrastructure.cache.entity.PkceRedisEntity
import org.springframework.stereotype.Component

@Component
class PkceCacheMapper {
    fun toDomain(pkceRedisEntity: PkceRedisEntity): Pkce =
        Pkce(
            verifier = pkceRedisEntity.verifier,
            challenge = pkceRedisEntity.challenge,
        )

    fun toEntity(pkce: Pkce): PkceRedisEntity =
        PkceRedisEntity(
            verifier = pkce.verifier,
            challenge = pkce.challenge,
        )
}
