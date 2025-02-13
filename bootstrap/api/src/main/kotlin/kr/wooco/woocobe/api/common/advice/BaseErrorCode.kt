package kr.wooco.woocobe.core.common.exception

import org.springframework.http.HttpStatus

enum class BaseErrorCode(
    val message: String,
    val status: HttpStatus,
) {
    INVALID_REQUEST_ERROR("missing request body or request parameters", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_ERROR("authentication failed", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED_ERROR_CODE("access denied", HttpStatus.FORBIDDEN),
    METHOD_NOT_SUPPORT_ERROR("method not supported", HttpStatus.METHOD_NOT_ALLOWED),
    NO_RESOURCE_ERROR("no such resource", HttpStatus.NOT_FOUND),
    UNKNOWN_ERROR("not included in any exception class", HttpStatus.INTERNAL_SERVER_ERROR),
}
