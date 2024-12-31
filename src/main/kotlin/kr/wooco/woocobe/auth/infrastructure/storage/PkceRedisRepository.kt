package kr.wooco.woocobe.auth.infrastructure.storage

import kr.wooco.woocobe.common.utils.getAndDeleteWithDeserialize
import kr.wooco.woocobe.common.utils.setWithSerialize
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class PkceRedisRepository(
    private val redisTemplate: StringRedisTemplate,
) {
    fun save(pkceEntity: PkceEntity): PkceEntity {
        redisTemplate.opsForValue().setWithSerialize(
            key = generateStorageKey(pkceEntity.challenge),
            value = pkceEntity,
            timeout = TIMEOUT,
        )
        return pkceEntity
    }

    fun findAndDeleteByChallenge(challenge: String): PkceEntity? =
        redisTemplate.opsForValue().getAndDeleteWithDeserialize(
            key = generateStorageKey(challenge),
            convertClass = PkceEntity::class,
        )

    companion object {
        private const val TIMEOUT = 10 * 1000L // 10초

        fun generateStorageKey(challenge: String) = "pkce:$challenge"
    }
}
