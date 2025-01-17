package kr.wooco.woocobe.auth.infrastructure.cache.repository

import kr.wooco.woocobe.auth.infrastructure.cache.entity.PkceRedisEntity
import kr.wooco.woocobe.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.common.utils.setWithSerialize
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PkceRedisRepository(
    private val redisTemplate: StringRedisTemplate,
) {
    fun save(pkceRedisEntity: PkceRedisEntity): PkceRedisEntity {
        redisTemplate.opsForValue().setWithSerialize(
            key = generateStorageKey(pkceRedisEntity.challenge),
            value = pkceRedisEntity,
            timeout = TIMEOUT,
        )
        return pkceRedisEntity
    }

    fun findAndDeleteByChallenge(challenge: String): PkceRedisEntity? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = generateStorageKey(challenge),
            convertClass = PkceRedisEntity::class,
        )

    companion object {
        private const val TIMEOUT = 1800 * 1000L // 30분

        private fun generateStorageKey(challenge: String) = "pkce:$challenge"
    }
}
