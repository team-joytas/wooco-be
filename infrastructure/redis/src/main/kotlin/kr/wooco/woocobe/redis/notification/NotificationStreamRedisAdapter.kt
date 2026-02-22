package kr.wooco.woocobe.redis.notification

import kr.wooco.woocobe.core.notification.domain.event.NotificationCreatedEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.connection.RedisStreamCommands.XAddOptions
import org.springframework.data.redis.connection.stream.StreamRecords
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class NotificationStreamRedisAdapter(
    private val redisTemplate: StringRedisTemplate,
    @Value("\${notification.stream.name:notification:send}")
    private val streamName: String,
    @Value("\${notification.stream.group:notification-sender-group}")
    private val groupName: String,
    @Value("\${notification.stream.max-len:100000}")
    private val maxLen: Long,
) {
    fun addToStream(event: NotificationCreatedEvent) {
        val payload = NotificationStreamPayload.from(event)
        val byteFields = payload.toFieldMap().entries.associate { (k, v) ->
            k.toByteArray() to v.toByteArray()
        }
        val record = StreamRecords.rawBytes(byteFields).withStreamKey(streamName.toByteArray())
        val options = XAddOptions.maxlen(maxLen).approximateTrimming(true)

        redisTemplate.execute { connection ->
            connection.streamCommands().xAdd(record, options)
        }
    }

    fun acknowledgeMessage(messageId: String) {
        redisTemplate.opsForStream<String, String>().acknowledge(streamName, groupName, messageId)
    }
}
