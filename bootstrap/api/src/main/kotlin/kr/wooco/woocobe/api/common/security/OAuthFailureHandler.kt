package kr.wooco.woocobe.api.common.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuthFailureHandler : AuthenticationFailureHandler {
    /**
     * GlobalExceptionAdvice 클래스를 타기 위해 아무런 작업을 진행하지 않고 예외를 던집니다.
     *
     * @see kr.wooco.woocobe.api.common.advice.GlobalExceptionAdvice
     * @author JiHongKim98
     */
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException,
    ) = throw exception
}
