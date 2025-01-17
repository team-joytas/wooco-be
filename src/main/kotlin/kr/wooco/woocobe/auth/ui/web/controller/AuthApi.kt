package kr.wooco.woocobe.auth.ui.web.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.ui.web.controller.request.LoginRequest
import kr.wooco.woocobe.auth.ui.web.controller.response.ReissueTokenResponse
import kr.wooco.woocobe.auth.ui.web.controller.response.SocialLoginResponse
import kr.wooco.woocobe.auth.ui.web.controller.response.SocialLoginUrlResponse
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
        @CookieValue codeChallenge: String,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginResponse>

    @SecurityRequirement(name = "JWT")
    fun logout(
        @AuthenticationPrincipal userId: Long,
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit>

    fun reissue(
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<ReissueTokenResponse>

    @SecurityRequirement(name = "JWT")
    fun withdraw(
        @AuthenticationPrincipal userId: Long,
        @CookieValue token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit>
}
