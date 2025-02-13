package kr.wooco.woocobe.common.exception

abstract class CustomException(
    val code: String,
    override val message: String,
) : RuntimeException(message)
