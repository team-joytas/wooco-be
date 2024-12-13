package kr.wooco.woocobe.common.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

private val objectMapper = jacksonObjectMapper()

fun ValueOperations<String, String>.setWithSerialize(
    key: String,
    value: Any,
    timeout: Long? = null,
) {
    val valueAsString = objectMapper.writeValueAsString(value)
    timeout?.let { set(key, valueAsString, timeout, TimeUnit.MILLISECONDS) }
        ?: set(key, valueAsString)
}

fun <V : Any> ValueOperations<String, String>.getWithDeserialize(
    key: String,
    convertClass: KClass<V>,
): V? {
    val value = get(key)
    return value?.let { objectMapper.readValue(it, convertClass.java) }
}

fun <V : Any> ValueOperations<String, String>.getAndDeleteWithDeserialize(
    key: String,
    convertClass: KClass<V>,
): V? {
    val value = getAndDelete(key)
    return value?.let { objectMapper.readValue(it, convertClass.java) }
}
