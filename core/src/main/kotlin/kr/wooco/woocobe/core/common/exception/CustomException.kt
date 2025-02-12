package kr.wooco.woocobe.core.common.exception

import org.springframework.http.HttpStatus

open class CustomException(
    val code: String,
    val status: HttpStatus,
    override val message: String,
) : RuntimeException(message)
