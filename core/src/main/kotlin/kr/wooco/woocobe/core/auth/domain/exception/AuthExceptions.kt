package kr.wooco.woocobe.core.auth.domain.exception

import kr.wooco.woocobe.common.exception.CustomException

sealed class BaseAuthException(
    code: String,
    message: String,
) : CustomException(code = code, message = message)

data object InvalidTokenException : BaseAuthException(
    code = "INVALID_TOKEN",
    message = "유효하지 않는 토큰입니다.",
) {
    private fun readResolve(): Any = InvalidTokenException
}

data object NotExistsAuthCodeException : BaseAuthException(
    code = "NOT_EXISTS_CHALLENGE_CODE",
    message = "존재하지 않는 챌린지 코드입니다.",
) {
    private fun readResolve(): Any = NotExistsAuthCodeException
}

data object NotExistsTokenException : BaseAuthException(
    code = "NOT_EXISTS_AUTH_TOKEN",
    message = "존재하지 않는 토큰입니다.",
) {
    private fun readResolve(): Any = NotExistsTokenException
}
