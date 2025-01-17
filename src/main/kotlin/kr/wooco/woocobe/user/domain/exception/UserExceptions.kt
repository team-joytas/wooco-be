package kr.wooco.woocobe.user.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BaseUserException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object NotExistsUserException : BaseUserException(
    code = "NOT_EXISTS_USER",
    message = "not exists user",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsUserException
}
