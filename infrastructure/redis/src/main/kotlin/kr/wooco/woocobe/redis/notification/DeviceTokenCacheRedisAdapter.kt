package kr.wooco.woocobe.redis.notification

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken.Token
import kr.wooco.woocobe.core.notification.domain.vo.DeviceTokenStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@Primary
@Component
class DeviceTokenCacheRedisAdapter(
    @Qualifier("deviceTokenPersistenceAdapter") private val delegateQuery: DeviceTokenQueryPort,
    @Qualifier("deviceTokenPersistenceAdapter") private val delegateCommand: DeviceTokenCommandPort,
    private val redisTemplate: StringRedisTemplate,
    @Value("\${notification.device-token-cache.ttl-minutes:30}")
    private val ttlMinutes: Long,
) : DeviceTokenQueryPort,
    DeviceTokenCommandPort {
    private val inFlight = ConcurrentHashMap<Long, CompletableFuture<DeviceToken?>>()

    override fun findByUserIdWithActive(userId: Long): DeviceToken? {
        val cached = getFromCache(userId)
        if (cached != null) {
            log.debug { "[DEVICE-TOKEN-CACHE] hit: userId=$userId" }
            return cached
        }

        val future = CompletableFuture<DeviceToken?>()
        val existing = inFlight.putIfAbsent(userId, future)

        if (existing != null) {
            log.debug { "[DEVICE-TOKEN-CACHE] joined in-flight: userId=$userId" }
            return existing.join()
        }

        try {
            val token = delegateQuery.findByUserIdWithActive(userId)
            if (token != null) {
                putToCache(userId, token.token)
            }
            log.debug { "[DEVICE-TOKEN-CACHE] miss → DB: userId=$userId, found=${token != null}" }
            future.complete(token)
            return token
        } catch (e: Exception) {
            future.completeExceptionally(e)
            throw e
        } finally {
            inFlight.remove(userId)
        }
    }

    override fun getByDeviceTokenId(deviceTokenId: Long): DeviceToken = delegateQuery.getByDeviceTokenId(deviceTokenId)

    override fun getByUserIdAndToken(
        userId: Long,
        token: Token,
    ): DeviceToken = delegateQuery.getByUserIdAndToken(userId, token)

    override fun getAllByUserIdWithActive(userId: Long): List<DeviceToken> = delegateQuery.getAllByUserIdWithActive(userId)

    override fun saveDeviceToken(deviceToken: DeviceToken): Long {
        val id = delegateCommand.saveDeviceToken(deviceToken)
        when (deviceToken.status) {
            DeviceTokenStatus.ACTIVE -> putToCache(deviceToken.userId, deviceToken.token)
            DeviceTokenStatus.DELETED -> evictCache(deviceToken.userId)
        }
        return id
    }

    private fun getFromCache(userId: Long): DeviceToken? {
        val value = redisTemplate.opsForValue().get(cacheKey(userId)) ?: return null
        return DeviceToken(
            id = 0L,
            userId = userId,
            token = Token(value),
            status = DeviceTokenStatus.ACTIVE,
        )
    }

    private fun putToCache(
        userId: Long,
        token: Token,
    ) {
        redisTemplate.opsForValue().set(cacheKey(userId), token.value, Duration.ofMinutes(ttlMinutes))
    }

    private fun evictCache(userId: Long) {
        redisTemplate.delete(cacheKey(userId))
    }

    private fun cacheKey(userId: Long): String = "$KEY_PREFIX$userId"

    companion object {
        private val log = KotlinLogging.logger {}
        private const val KEY_PREFIX = "device-token:user:"
    }
}
