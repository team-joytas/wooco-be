package kr.wooco.woocobe.core.user.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseUserException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsUserException : BaseUserException(
    code = "NOT_EXISTS_USER",
    message = "존재하지 않는 사용자입니다.",
) {
    private fun readResolve(): Any = NotExistsUserException
}

data object InActiveUserException : BaseUserException(
    code = "INACTIVE_USER",
    message = "비활성화된 사용자입니다.",
) {
    private fun readResolve(): Any = InActiveUserException
}
