package kr.wooco.woocobe.api.common.advice

import org.springframework.http.HttpStatus

enum class BaseErrorCode(
    val message: String,
    val status: HttpStatus,
) {
    INVALID_REQUEST_ERROR("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_ERROR("인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED_ERROR_CODE("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
}
