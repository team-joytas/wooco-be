package kr.wooco.woocobe.common.exception

open class CustomException(
    val code: String,
    override val message: String,
) : RuntimeException(message)
