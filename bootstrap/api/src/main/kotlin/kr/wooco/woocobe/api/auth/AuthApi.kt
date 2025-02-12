package kr.wooco.woocobe.api.auth

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.auth.request.LoginRequest
import kr.wooco.woocobe.api.auth.response.SocialLoginUrlResponse
import kr.wooco.woocobe.api.auth.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "인증 API")
interface AuthApi {
    fun socialLoginUrl(
        @PathVariable provider: String,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginUrlResponse>

    fun socialLogin(
        @RequestBody request: LoginRequest,
        @PathVariable provider: String,
        @CookieValue authCodeId: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse>

    @SecurityRequirement(name = "JWT")
    fun logout(
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit>

    fun reissue(
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse>

    @SecurityRequirement(name = "JWT")
    fun withdraw(
        @AuthenticationPrincipal userId: Long,
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit>
}
