package kr.wooco.woocobe.redis.notification

import kr.wooco.woocobe.redis.common.stream.RedisStreamMessagePollingContainer
import kr.wooco.woocobe.redis.common.stream.RedisStreamPendingPollingContainer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import java.time.Duration

@Configuration
class NotificationStreamConfig(
    @Value("\${notification.stream.name:notification:send}")
    private val streamName: String,
    @Value("\${notification.stream.group:notification-sender-group}")
    private val groupName: String,
    @Value("\${notification.stream.consumer:consumer-1}")
    private val consumerName: String,
) {
    @Bean
    fun notificationMessagePollingContainer(
        redisTemplate: StringRedisTemplate,
        listener: NotificationStreamMessageListener,
    ): RedisStreamMessagePollingContainer =
        RedisStreamMessagePollingContainer(
            redisTemplate = redisTemplate,
            streamName = streamName,
            groupName = groupName,
            consumerName = consumerName,
            listener = listener,
            batchSize = 100,
            blockTimeout = Duration.ofSeconds(2),
        )

    @Bean
    fun notificationPendingPollingContainer(
        redisTemplate: StringRedisTemplate,
        connectionFactory: LettuceConnectionFactory,
        listener: NotificationStreamMessageListener,
    ): RedisStreamPendingPollingContainer =
        RedisStreamPendingPollingContainer(
            redisTemplate = redisTemplate,
            connectionFactory = connectionFactory,
            streamName = streamName,
            groupName = groupName,
            consumerName = consumerName,
            listener = listener,
            batchSize = 100,
            minIdleTime = Duration.ofSeconds(10),
            pollInterval = Duration.ofSeconds(2),
        )
}
