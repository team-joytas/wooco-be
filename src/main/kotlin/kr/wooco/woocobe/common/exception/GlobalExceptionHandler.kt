package kr.wooco.woocobe.common.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

private val logger = KotlinLogging.logger {}

data class GlobalExceptionResponse(
    val code: String,
    val message: String? = "",
)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<GlobalExceptionResponse> {
        val body = GlobalExceptionResponse(code = e.code, message = e.message)
        return ResponseEntity.status(e.status).body(body)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<GlobalExceptionResponse> {
        val body = GlobalExceptionResponse(code = SECURITY_ERROR_CODE, message = e.message)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<GlobalExceptionResponse> {
        val body = GlobalExceptionResponse(code = SECURITY_ERROR_CODE, message = e.message)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<GlobalExceptionResponse> {
        val errorCode = BaseErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH
        val body = GlobalExceptionResponse(code = errorCode.code, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<GlobalExceptionResponse> {
        val errorCode = BaseErrorCode.METHOD_NOT_SUPPORT
        val body = GlobalExceptionResponse(code = errorCode.code, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<GlobalExceptionResponse> {
        val errorCode = BaseErrorCode.NO_RESOURCE
        val body = GlobalExceptionResponse(code = errorCode.code, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnCatchException(e: Exception): ResponseEntity<GlobalExceptionResponse> {
        logger.error(e) { UN_CATCH_LOG_FORMAT.format(e::class, e.message) }

        val errorCode = BaseErrorCode.UNKNOWN
        val body = GlobalExceptionResponse(code = errorCode.code, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    companion object {
        private const val SECURITY_ERROR_CODE = "SECURITY_AUTHENTICATION_FAILED"
        private const val UN_CATCH_LOG_FORMAT =
            "GlobalExceptionHandler::handleUnCatchException\n" +
                "| >> EXCEPTION_TYPE: %s\n" +
                "| >> EXCEPTION_MESSAGE: %s"
    }
}
