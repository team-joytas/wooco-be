package kr.wooco.woocobe.auth.ui.web

import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.domain.usecase.LogoutInput
import kr.wooco.woocobe.auth.domain.usecase.LogoutUseCase
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenInput
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenUseCase
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginInput
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginUseCase
import kr.wooco.woocobe.auth.ui.web.dto.request.LoginRequest
import kr.wooco.woocobe.auth.ui.web.dto.response.ReissueTokenResponse
import kr.wooco.woocobe.auth.ui.web.dto.response.SocialLoginResponse
import kr.wooco.woocobe.common.utils.addCookie
import kr.wooco.woocobe.common.utils.deleteCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val logoutUseCase: LogoutUseCase,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
) {
    @PostMapping("/logout")
    fun logout(
        @AuthenticationPrincipal userId: Long,
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
        response: HttpServletResponse,
    ): ResponseEntity<Void> {
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
    fun reissue(
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

    @PostMapping("/login")
    fun socialLogin(
        @RequestBody request: LoginRequest,
        response: HttpServletResponse,
    ): ResponseEntity<SocialLoginResponse> {
        val result = socialLoginUseCase.execute(
            SocialLoginInput(
                socialToken = request.code,
                socialType = request.socialType,
            ),
        )
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, result.refreshToken)
        return ResponseEntity.ok(SocialLoginResponse.from(result))
    }

    companion object {
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh_token"
    }
}
