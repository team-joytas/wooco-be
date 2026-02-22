package kr.wooco.woocobe.redis.notification

import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenCommandPort
import kr.wooco.woocobe.core.notification.application.port.out.DeviceTokenQueryPort
import kr.wooco.woocobe.core.notification.domain.entity.DeviceToken
import kr.wooco.woocobe.core.notification.domain.vo.DeviceTokenStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@org.mockito.junit.jupiter.MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class DeviceTokenCacheRedisAdapterTest {
    @Mock
    lateinit var delegate: DeviceTokenQueryPort

    @Mock
    lateinit var delegateCommand: DeviceTokenCommandPort

    @Mock
    lateinit var redisTemplate: org.springframework.data.redis.core.StringRedisTemplate

    @Mock
    lateinit var valueOps: org.springframework.data.redis.core.ValueOperations<String, String>

    private lateinit var adapter: DeviceTokenCacheRedisAdapter

    @BeforeEach
    fun setUp() {
        given(redisTemplate.opsForValue()).willReturn(valueOps)
        adapter = DeviceTokenCacheRedisAdapter(
            delegateQuery = delegate,
            delegateCommand = delegateCommand,
            redisTemplate = redisTemplate,
            ttlMinutes = 30L,
        )
    }

    @Test
    @DisplayName("캐시 미스 시 DB 조회 후 캐시에 저장한다")
    fun cacheMissThenDbLookupAndCachePut() {
        val userId = 100L
        val token = DeviceToken(
            id = 1L,
            userId = userId,
            token = DeviceToken.Token("fcm-token-abc"),
            status = DeviceTokenStatus.ACTIVE,
        )

        given(valueOps.get("device-token:user:$userId")).willReturn(null)
        given(delegate.findByUserIdWithActive(userId)).willReturn(token)

        val result = adapter.findByUserIdWithActive(userId)

        assertThat(result).isNotNull
        assertThat(result!!.token.value).isEqualTo("fcm-token-abc")
        then(delegate).should().findByUserIdWithActive(userId)
        then(valueOps).should().set(
            Mockito.eq("device-token:user:$userId"),
            Mockito.eq("fcm-token-abc"),
            Mockito.any(java.time.Duration::class.java),
        )
    }

    @Test
    @DisplayName("캐시 히트 시 DB를 조회하지 않는다")
    fun cacheHitSkipsDb() {
        val userId = 100L

        given(valueOps.get("device-token:user:$userId")).willReturn("fcm-token-cached")

        val result = adapter.findByUserIdWithActive(userId)

        assertThat(result).isNotNull
        assertThat(result!!.token.value).isEqualTo("fcm-token-cached")
        then(delegate).shouldHaveNoInteractions()
    }

    @Test
    @DisplayName("DB에 토큰이 없으면 null을 반환하고 캐시에 저장하지 않는다")
    fun dbMissReturnsNullNoCachePut() {
        val userId = 100L

        given(valueOps.get("device-token:user:$userId")).willReturn(null)
        given(delegate.findByUserIdWithActive(userId)).willReturn(null)

        val result = adapter.findByUserIdWithActive(userId)

        assertThat(result).isNull()
        then(valueOps).should(Mockito.never()).set(
            Mockito.anyString(),
            Mockito.anyString(),
            Mockito.any(java.time.Duration::class.java),
        )
    }

    @Test
    @DisplayName("ACTIVE 토큰 저장 시 캐시에 write-through 한다")
    fun writeThroughOnActiveSave() {
        val token = DeviceToken(
            id = 1L,
            userId = 100L,
            token = DeviceToken.Token("new-token"),
            status = DeviceTokenStatus.ACTIVE,
        )
        given(delegateCommand.saveDeviceToken(token)).willReturn(1L)

        adapter.saveDeviceToken(token)

        then(valueOps).should().set(
            Mockito.eq("device-token:user:100"),
            Mockito.eq("new-token"),
            Mockito.any(java.time.Duration::class.java),
        )
    }

    @Test
    @DisplayName("DELETED 토큰 저장 시 캐시를 삭제한다")
    fun evictCacheOnDeletedSave() {
        val token = DeviceToken(
            id = 1L,
            userId = 100L,
            token = DeviceToken.Token("old-token"),
            status = DeviceTokenStatus.DELETED,
        )
        given(delegateCommand.saveDeviceToken(token)).willReturn(1L)

        adapter.saveDeviceToken(token)

        then(redisTemplate).should().delete("device-token:user:100")
    }
}
