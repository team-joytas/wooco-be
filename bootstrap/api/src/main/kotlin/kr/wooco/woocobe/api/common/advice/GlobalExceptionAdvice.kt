package kr.wooco.woocobe.api.common.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.api.common.utils.JwtUtils
import kr.wooco.woocobe.common.exception.CustomException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class GlobalExceptionAdvice {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<BaseErrorResponse> {
        log.warn { e.localizedMessage }

        val body = BaseErrorResponse(code = e.code, message = e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(
        value = [
            JwtUtils.JwtException::class,
            AuthenticationException::class,
        ],
    )
    fun handleAuthenticationException(e: Exception): ResponseEntity<BaseErrorResponse> {
        log.warn { e.localizedMessage }

        val errorCode = BaseErrorCode.AUTHENTICATION_ERROR
        val body = BaseErrorResponse(code = errorCode.name, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<BaseErrorResponse> {
        log.warn { e.localizedMessage }

        val errorCode = BaseErrorCode.ACCESS_DENIED_ERROR_CODE
        val body = BaseErrorResponse(code = errorCode.name, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(
        value = [
            IllegalArgumentException::class,
            NoResourceFoundException::class,
            MissingRequestCookieException::class,
            MethodArgumentNotValidException::class,
            HttpMessageNotReadableException::class,
            MethodArgumentTypeMismatchException::class,
            HttpRequestMethodNotSupportedException::class,
        ],
    )
    fun handleInvalidRequestException(e: Exception): ResponseEntity<BaseErrorResponse> {
        log.warn { e.localizedMessage }

        val errorCode = BaseErrorCode.INVALID_REQUEST_ERROR
        val body = BaseErrorResponse(code = errorCode.name, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnCatchException(e: Exception): ResponseEntity<BaseErrorResponse> {
        log.error(e) { e.javaClass }

        val errorCode = BaseErrorCode.UNKNOWN_ERROR
        val body = BaseErrorResponse(code = errorCode.name, message = errorCode.message)
        return ResponseEntity.status(errorCode.status).body(body)
    }

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
