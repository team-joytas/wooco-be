package kr.wooco.woocobe.redis.auth.repository

import kr.wooco.woocobe.redis.auth.entity.AuthCodeRedisEntity
import kr.wooco.woocobe.redis.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.redis.common.utils.setWithSerialize
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class AuthCodeRedisRepository(
    private val redisTemplate: StringRedisTemplate,
) {
    fun save(authCodeRedisEntity: AuthCodeRedisEntity): AuthCodeRedisEntity {
        redisTemplate.opsForValue().setWithSerialize(
            key = generateStorageKey(authCodeRedisEntity.id),
            value = authCodeRedisEntity,
            timeout = TIMEOUT,
        )
        return authCodeRedisEntity
    }

    fun findAndDeleteByAuthCodeId(authCodeId: String): AuthCodeRedisEntity? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = generateStorageKey(authCodeId),
            convertClass = AuthCodeRedisEntity::class,
        )

    companion object {
        private const val TIMEOUT = 1800 * 1000L // 30분

        private fun generateStorageKey(authCodeId: String) = "auth:code:$authCodeId"
    }
}
