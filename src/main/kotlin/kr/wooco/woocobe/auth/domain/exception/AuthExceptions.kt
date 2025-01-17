package kr.wooco.woocobe.auth.domain.exception

import kr.wooco.woocobe.common.domain.exception.CustomException
import org.springframework.http.HttpStatus

sealed class BaseAuthException(
    code: String,
    message: String,
    status: HttpStatus,
) : CustomException(code = code, message = message, status = status)

data object UnSupportSocialTypeException : BaseAuthException(
    code = "UN_SUPPORT_SOCIAL_TYPE",
    message = "지원하지 않는 소셜 타입입니다.",
    status = HttpStatus.BAD_REQUEST,
) {
    private fun readResolve(): Any = UnSupportSocialTypeException
}

data object InvalidTokenException : BaseAuthException(
    code = "INVALID_TOKEN",
    message = "유효하지 않는 토큰입니다.",
    status = HttpStatus.UNAUTHORIZED,
) {
    private fun readResolve(): Any = InvalidTokenException
}

data object NotExistsChallengeCodeException : BaseAuthException(
    code = "NOT_EXISTS_CHALLENGE_CODE",
    message = "존재하지 않는 챌린지 코드입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsChallengeCodeException
}

data object NotExistsTokenException : BaseAuthException(
    code = "NOT_EXISTS_AUTH_TOKEN",
    message = "존재하지 않는 토큰입니다.",
    status = HttpStatus.NOT_FOUND,
) {
    private fun readResolve(): Any = NotExistsTokenException
}
