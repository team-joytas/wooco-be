package kr.wooco.woocobe.redis.common.stream

fun interface StreamPendingListener {
    fun onPendingMessages(messages: List<StreamPendingMessage>)
}
