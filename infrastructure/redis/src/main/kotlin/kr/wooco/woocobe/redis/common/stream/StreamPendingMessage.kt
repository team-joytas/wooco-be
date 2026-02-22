package kr.wooco.woocobe.redis.common.stream

import org.springframework.data.redis.connection.stream.MapRecord

data class StreamPendingMessage(
    val record: MapRecord<String, String, String>,
    val deliveryCount: Long,
)
