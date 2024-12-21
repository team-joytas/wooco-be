package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.common.utils.getWithDeserialize
import kr.wooco.woocobe.common.utils.setWithSerialize
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class AuthTokenRedisRepository(
    private val redisTemplate: StringRedisTemplate,
    @Value("\${app.jwt.expiration.refresh-token}") private val timeout: Long,
) {
    fun save(authTokenEntity: AuthTokenEntity): AuthTokenEntity {
        redisTemplate.opsForValue().setWithSerialize(
            key = generateStorageKey(authTokenEntity.id),
            value = authTokenEntity,
            timeout = timeout,
        )
        return authTokenEntity
    }

    fun findById(id: Long): AuthTokenEntity? =
        redisTemplate.opsForValue().getWithDeserialize(
            key = generateStorageKey(id),
            convertClass = AuthTokenEntity::class,
        )

    fun findAndDeleteById(id: Long): AuthTokenEntity? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = generateStorageKey(id),
            convertClass = AuthTokenEntity::class,
        )

    fun deleteById(id: Long) {
        redisTemplate.delete(generateStorageKey(id))
    }

    companion object {
        fun generateStorageKey(id: Long) = "token:$id"
    }
}
