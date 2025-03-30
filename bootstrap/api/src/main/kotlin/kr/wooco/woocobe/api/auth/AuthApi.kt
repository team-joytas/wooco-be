package kr.wooco.woocobe.api.auth

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.auth.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue

@Tag(name = "인증 API")
interface AuthApi {
    fun reissueToken(
        @CookieValue refreshToken: String,
        response: HttpServletResponse,
    ): ResponseEntity<TokenResponse>

    @SecurityRequirement(name = "JWT")
    fun logout(
        @CookieValue refreshToken: String?,
        response: HttpServletResponse,
    ): ResponseEntity<Unit>
}
