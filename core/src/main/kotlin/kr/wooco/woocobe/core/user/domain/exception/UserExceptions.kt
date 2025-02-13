package kr.wooco.woocobe.core.user.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseUserException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsUserException : BaseUserException(
    code = "NOT_EXISTS_USER",
    message = "not exists user",
) {
    private fun readResolve(): Any = NotExistsUserException
}
