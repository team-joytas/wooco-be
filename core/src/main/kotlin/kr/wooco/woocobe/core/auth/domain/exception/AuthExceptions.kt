package kr.wooco.woocobe.core.auth.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseAuthException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object NotExistsTokenException : BaseAuthException(
    code = "NOT_EXISTS_TOKEN",
    message = "존재하지 않는 토큰입니다.",
) {
    private fun readResolve(): Any = NotExistsTokenException
}
