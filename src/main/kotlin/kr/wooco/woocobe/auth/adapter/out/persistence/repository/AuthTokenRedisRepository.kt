package kr.wooco.woocobe.auth.adapter.out.persistence.repository

import kr.wooco.woocobe.auth.adapter.out.persistence.entity.AuthTokenRedisEntity
import kr.wooco.woocobe.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.common.utils.setWithSerialize
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class AuthTokenRedisRepository(
    private val redisTemplate: StringRedisTemplate,
    @Value("\${app.jwt.expiration.refresh-token}") private val timeout: Long,
) {
    fun save(authTokenEntity: AuthTokenRedisEntity): AuthTokenRedisEntity {
        redisTemplate.opsForValue().setWithSerialize(
            key = generateStorageKey(authTokenEntity.id),
            value = authTokenEntity,
            timeout = timeout,
        )
        return authTokenEntity
    }

    fun findAndDeleteById(id: String): AuthTokenRedisEntity? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = generateStorageKey(id),
            convertClass = AuthTokenRedisEntity::class,
        )

    fun deleteById(id: String) {
        redisTemplate.delete(generateStorageKey(id))
    }

    companion object {
        fun generateStorageKey(id: String) = "auth:token:$id"
    }
}
