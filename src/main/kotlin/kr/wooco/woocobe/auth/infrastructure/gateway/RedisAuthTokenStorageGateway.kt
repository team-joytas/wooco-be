package kr.wooco.woocobe.auth.infrastructure.gateway

import kr.wooco.woocobe.auth.domain.gateway.AuthTokenStorageGateway
import kr.wooco.woocobe.auth.domain.model.AuthToken
import kr.wooco.woocobe.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.common.utils.setWithSerialize
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

private const val TOKEN_PREFIX = "token:"

@Component
class RedisAuthTokenStorageGateway(
    private val redisTemplate: StringRedisTemplate,
    @Value("\${app.jwt.expiration.refresh-token}") private val timeout: Long,
) : AuthTokenStorageGateway {
    override fun save(authToken: AuthToken): AuthToken {
        redisTemplate.opsForValue().setWithSerialize(
            key = TOKEN_PREFIX + authToken.tokenId,
            value = authToken,
            timeout = timeout,
        )
        return authToken
    }

    override fun getByTokenId(tokenId: Long): AuthToken? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = TOKEN_PREFIX + tokenId,
            convertClass = AuthToken::class,
        )
}
