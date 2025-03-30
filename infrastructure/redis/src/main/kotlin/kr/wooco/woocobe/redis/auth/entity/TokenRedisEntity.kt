package kr.wooco.woocobe.redis.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

private const val NINETY_DAYS = 60 * 60 * 24 * 90L // 90일

@RedisHash(value = "token", timeToLive = NINETY_DAYS)
class TokenRedisEntity(
    val userId: Long,
    @Id
    val id: Long,
)
