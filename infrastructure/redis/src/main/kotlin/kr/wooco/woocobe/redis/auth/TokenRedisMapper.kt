package kr.wooco.woocobe.redis.auth

import kr.wooco.woocobe.common.tsid.TsidGenerator
import kr.wooco.woocobe.core.auth.domain.entity.Token
import kr.wooco.woocobe.redis.auth.entity.TokenRedisEntity

internal object TokenRedisMapper {
    fun toDomainEntity(tokenRedisEntity: TokenRedisEntity): Token =
        Token(
            id = tokenRedisEntity.id,
            userId = tokenRedisEntity.userId,
        )

    fun toRedisEntity(token: Token): TokenRedisEntity =
        TokenRedisEntity(
            id = when {
                token.id == 0L -> TsidGenerator.generateToLong()
                else -> token.id
            },
            userId = token.userId,
        )
}
