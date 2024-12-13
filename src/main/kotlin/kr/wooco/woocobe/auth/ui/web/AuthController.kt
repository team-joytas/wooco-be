package kr.wooco.woocobe.auth.ui.web

import kr.wooco.woocobe.auth.domain.usecase.LogoutInput
import kr.wooco.woocobe.auth.domain.usecase.LogoutUseCase
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenInput
import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenUseCase
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginInput
import kr.wooco.woocobe.auth.domain.usecase.SocialLoginUseCase
import kr.wooco.woocobe.auth.ui.web.dto.request.LoginRequest
import kr.wooco.woocobe.auth.ui.web.dto.response.ReissueTokenResponse
import kr.wooco.woocobe.auth.ui.web.dto.response.SocialLoginResponse
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
    // TODO: 응답시 쿠키 핸들링 로직 필요 (확장 확장함수 구현 예정)

    @PostMapping("/logout")
    fun logout(
        @AuthenticationPrincipal userId: Long,
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
    ): ResponseEntity<Void> {
        logoutUseCase.execute(
            LogoutInput(
                userId = userId,
                refreshToken = token,
            ),
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/reissue")
    fun reissue(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) token: String,
    ): ResponseEntity<ReissueTokenResponse> {
        val result = reissueTokenUseCase.execute(
            ReissueTokenInput(
                refreshToken = token,
            ),
        )
        return ResponseEntity.ok(ReissueTokenResponse.from(result))
    }

    @PostMapping("/login")
    fun socialLogin(
        @RequestBody request: LoginRequest,
    ): ResponseEntity<SocialLoginResponse> {
        val result = socialLoginUseCase.execute(
            SocialLoginInput(
                socialToken = request.code,
                socialType = request.socialType,
            ),
        )
        return ResponseEntity.ok(SocialLoginResponse.from(result))
    }

    companion object {
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh_token"
    }
}
