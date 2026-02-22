package kr.wooco.woocobe.redis.common.stream

import org.springframework.data.redis.connection.stream.MapRecord

fun interface StreamMessageListener {
    fun onMessages(messages: List<MapRecord<String, String, String>>)
}
