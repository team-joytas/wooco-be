package kr.wooco.woocobe.auth.ui.web.controller

import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.domain.usecase.GetSocialLoginUrlInput
import kr.wooco.woocobe.auth.domain.usecase.GetSocialLoginUrlUseCase
import kr.wooco.woocobe.auth.domain.usecase.LogoutInput
import kr.wooco.woocobe.auth.domain.usecase.LogoutUseCase
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenInput
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenUseCase
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginInput
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginUseCase
import kr.wooco.woocobe.auth.domain.usecase.WithdrawInput
import kr.wooco.woocobe.auth.domain.usecase.WithdrawUseCase
import kr.wooco.woocobe.auth.ui.web.controller.request.LoginRequest
import kr.wooco.woocobe.auth.ui.web.controller.response.ReissueTokenResponse
import kr.wooco.woocobe.auth.ui.web.controller.response.SocialLoginResponse
import kr.wooco.woocobe.auth.ui.web.controller.response.SocialLoginUrlResponse
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
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val getSocialLoginUrlUseCase: GetSocialLoginUrlUseCase,
) : AuthApi {
    @GetMapping("/{provider}/social-login/url")
    override fun socialLoginUrl(
        @PathVariable provider: String,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginUrlResponse> {
        val results = getSocialLoginUrlUseCase.execute(
            GetSocialLoginUrlInput(
                socialType = provider,
            ),
        )
        response.addCookie(CODE_CHALLENGE_COOKIE_NAME, results.challenge)
        return ResponseEntity.ok(SocialLoginUrlResponse.from(results))
    }

    @PostMapping("/{provider}/social-login")
    override fun socialLogin(
        @RequestBody request: LoginRequest,
        @PathVariable provider: String,
        @CookieValue(CODE_CHALLENGE_COOKIE_NAME) codeChallenge: String,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginResponse> {
        val result = socialLoginUseCase.execute(
            SocialLoginInput(
                authCode = request.code,
                socialType = provider,
                challenge = codeChallenge,
            ),
        )
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, result.refreshToken)
        response.deleteCookie(CODE_CHALLENGE_COOKIE_NAME)
        return ResponseEntity.ok(SocialLoginResponse.from(result))
    }

    @PostMapping("/logout")
    override fun logout(
        @AuthenticationPrincipal userId: Long,
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit> {
        logoutUseCase.execute(
            LogoutInput(
                userId = userId,
                refreshToken = token,
            ),
        )
        response.deleteCookie(REFRESH_TOKEN_COOKIE_NAME)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/reissue")
    override fun reissue(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<ReissueTokenResponse> {
        val result = reissueTokenUseCase.execute(
            ReissueTokenInput(
                refreshToken = token,
            ),
        )
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, result.refreshToken)
        return ResponseEntity.ok(ReissueTokenResponse.from(result))
    }

    @DeleteMapping("/withdraw")
    override fun withdraw(
        @AuthenticationPrincipal userId: Long,
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Unit> {
        withdrawUseCase.execute(
            WithdrawInput(
                userId = userId,
                refreshToken = token,
            ),
        )
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh_token"
        private const val CODE_CHALLENGE_COOKIE_NAME = "code_challenge"
    }
}
