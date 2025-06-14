package kr.wooco.woocobe.api.common.advice

import java.time.Instant

data class BaseApiResponse(
    val path: String,
    val results: Any? = null,
    val timestamp: Long = Instant.now().epochSecond,
)

data class BaseErrorResponse(
    val code: String,
    val message: String?,
)
