package kr.wooco.woocobe.api.auth

import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.auth.response.TokenResponse
import kr.wooco.woocobe.api.common.utils.JwtUtils
import kr.wooco.woocobe.api.common.utils.addCookie
import kr.wooco.woocobe.api.common.utils.deleteCookie
import kr.wooco.woocobe.core.auth.application.port.`in`.DeleteTokenUseCase
import kr.wooco.woocobe.core.auth.application.port.`in`.ReissueTokenUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val reissueTokenUseCase: ReissueTokenUseCase,
) : AuthApi {
    @PostMapping("/reissue")
    override fun reissueToken(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME) refreshToken: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse> {
        val (userId, tokenId) = JwtUtils.extractRefreshToken(refreshToken)
        val command = ReissueTokenUseCase.Command(
            userId = userId,
            tokenId = tokenId,
        )
        val rotateRefreshToken = reissueTokenUseCase.reissueToken(command).let {
            JwtUtils.createRefreshToken(userId, it)
        }
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, rotateRefreshToken)
        val accessToken = JwtUtils.createAccessToken(userId)
        return ResponseEntity.ok(TokenResponse(accessToken))
    }

    @PostMapping("/logout")
    override fun logout(
        @CookieValue(REFRESH_TOKEN_COOKIE_NAME, required = false) refreshToken: String?,
        response: HttpServletResponse,
    ): ResponseEntity<Unit> {
        refreshToken?.run {
            val (userId, tokenId) = JwtUtils.extractRefreshToken(refreshToken)
            val command = DeleteTokenUseCase.Command(
                userId = userId,
                tokenId = tokenId,
            )
            deleteTokenUseCase.deleteToken(command)
        }
        response.deleteCookie(REFRESH_TOKEN_COOKIE_NAME)
        return ResponseEntity.ok().build()
    }

    companion object {
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh-token"
    }
}
