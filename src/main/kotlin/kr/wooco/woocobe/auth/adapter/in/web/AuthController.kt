package kr.wooco.woocobe.auth.adapter.`in`.web

import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.adapter.`in`.web.request.LoginRequest
import kr.wooco.woocobe.auth.adapter.`in`.web.response.SocialLoginUrlResponse
import kr.wooco.woocobe.auth.adapter.`in`.web.response.TokenResponse
import kr.wooco.woocobe.auth.application.port.`in`.LogoutUseCase
import kr.wooco.woocobe.auth.application.port.`in`.ReadSocialLoginUrlUseCase
import kr.wooco.woocobe.auth.application.port.`in`.ReissueAuthTokenUseCase
import kr.wooco.woocobe.auth.application.port.`in`.SocialLoginUseCase
import kr.wooco.woocobe.auth.application.port.`in`.WithdrawUseCase
import kr.wooco.woocobe.common.utils.addCookie
import kr.wooco.woocobe.common.utils.deleteCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val reissueAuthTokenUseCase: ReissueAuthTokenUseCase,
    private val readSocialLoginUrlUseCase: ReadSocialLoginUrlUseCase,
) : AuthApi {
    @GetMapping("/{provider}/social-login/url")
    override fun socialLoginUrl(
        @PathVariable provider: String,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginUrlResponse> {
        val results = readSocialLoginUrlUseCase.readSocialLoginUrl(provider)
        response.addCookie(AUTH_CODE_COOKIE_NAME, results.authCodeId)
        return ResponseEntity.ok(SocialLoginUrlResponse(results.loginUrl))
    }

    @PostMapping("/{provider}/social-login")
    override fun socialLogin(
        @RequestBody request: LoginRequest,
        @PathVariable provider: String,
        @CookieValue(AUTH_CODE_COOKIE_NAME) authCodeId: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse> {
        val results = socialLoginUseCase.socialLogin(
            code = request.code,
            socialType = provider,
            authCodeId = authCodeId,
        )
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, results.refreshToken)
        response.deleteCookie(AUTH_CODE_COOKIE_NAME)
        return ResponseEntity.ok(TokenResponse(results.accessToken))
    }

    @PostMapping("/logout")
    override fun logout(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit> {
        logoutUseCase.logout(token)
        response.deleteCookie(REFRESH_TOKEN_COOKIE_NAME)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/reissue")
    override fun reissue(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse> {
        val result = reissueAuthTokenUseCase.reissueAuthToken(token)
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, result.refreshToken)
        return ResponseEntity.ok(TokenResponse(result.accessToken))
    }

    @DeleteMapping("/withdraw")
    override fun withdraw(
        @AuthenticationPrincipal userId: Long,
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit> {
        withdrawUseCase.withdraw(userId, token)
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val AUTH_CODE_COOKIE_NAME = "auth_code"
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh_token"
    }
}
