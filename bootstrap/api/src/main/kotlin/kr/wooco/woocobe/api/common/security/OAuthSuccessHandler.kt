package kr.wooco.woocobe.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.common.advice.BaseApiResponse
import kr.wooco.woocobe.api.common.utils.JwtUtils
import kr.wooco.woocobe.api.common.utils.addCookie
import kr.wooco.woocobe.core.auth.application.port.`in`.IssueTokenUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuthSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val issueTokenUseCase: IssueTokenUseCase,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        require(authentication is OAuth2AuthenticationToken)
        val oAuth2User = authentication.principal as CustomOAuth2User
        val userId = oAuth2User.userId

        val command = IssueTokenUseCase.Command(userId = userId)
        val tokenId = issueTokenUseCase.issueToken(command)

        val accessToken = JwtUtils.createAccessToken(userId = userId)
        val refreshToken = JwtUtils.createRefreshToken(userId = userId, tokenId = tokenId)

        response.addCookie(SOCIAL_TOKEN_COOKIE_NAME, oAuth2User.socialToken, DEFAULT_SOCIAL_TOKEN_EXPIRES_SECONDS)
        response.addCookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken)

        val responseBody = BaseApiResponse(
            path = request.requestURI,
            results = OAuthSuccessResponse(accessToken),
        )
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.writer.write(objectMapper.writeValueAsString(responseBody))
    }

    companion object {
        private const val REFRESH_TOKEN_COOKIE_NAME = "refresh-token"

        private const val SOCIAL_TOKEN_COOKIE_NAME = "social-token"
        private const val DEFAULT_SOCIAL_TOKEN_EXPIRES_SECONDS = 60 * 60

        private data class OAuthSuccessResponse(
            val accessToken: String,
        )
    }
}
