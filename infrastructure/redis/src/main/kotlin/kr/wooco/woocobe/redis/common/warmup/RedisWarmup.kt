package kr.wooco.woocobe.redis.common.warmup

import kr.wooco.woocobe.common.warmup.AbstractWarmup
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

/**
 * Redis 연결 및 라이브러리 Warmup
 *
 * Redis 커넥션 풀 초기화 및 직렬화/역직렬화 코드 경로를 warmup합니다.
 */
@Component
@EnableConfigurationProperties(RedisWarmupProperties::class)
class RedisWarmup(
    private val redisTemplate: StringRedisTemplate,
    private val properties: RedisWarmupProperties,
) : AbstractWarmup() {
    override val name: String = NAME
    override val enabled: Boolean get() = properties.enabled
    override val iterations: Int get() = properties.iterations

    override fun doWarmup() {
        val key = "$KEY_PREFIX${Thread.currentThread().threadId()}:${System.nanoTime()}"

        redisTemplate.opsForValue().set(key, VALUE)
        redisTemplate.opsForValue().get(key)
        redisTemplate.delete(key)
        redisTemplate.connectionFactory?.connection?.ping()
    }

    companion object {
        const val NAME = "redis"
        private const val KEY_PREFIX = "warmup:health:"
        private const val VALUE = "warmup-test"
    }
}
