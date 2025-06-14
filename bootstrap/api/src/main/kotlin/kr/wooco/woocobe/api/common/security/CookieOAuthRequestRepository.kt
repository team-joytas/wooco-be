package kr.wooco.woocobe.api.common.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.common.utils.addCookie
import kr.wooco.woocobe.api.common.utils.deleteCookie
import kr.wooco.woocobe.api.common.utils.getCookie
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import java.util.Base64

@Component
class CookieOAuthRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest {
        val oAuth2AuthRequest = request.getCookie(OAUTH_AUTHORIZATION_REQUEST_COOKIE_NAME)
        require(oAuth2AuthRequest != null) { "쿠키(_oarc)가 존재하지 않는 요청입니다." }

        val oAuth2AuthRequestWrapper = deserialize(oAuth2AuthRequest.value)
        return oAuth2AuthRequestWrapper.toOAuth2AuthorizationRequest()
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        authorizationRequest?.let {
            response.addCookie(
                OAUTH_AUTHORIZATION_REQUEST_COOKIE_NAME,
                serialize(OAuth2AuthRequestWrapper.from(it)),
                DEFAULT_COOKIE_EXPIRED_AT,
            )
        }
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): OAuth2AuthorizationRequest {
        response.deleteCookie(OAUTH_AUTHORIZATION_REQUEST_COOKIE_NAME)
        return loadAuthorizationRequest(request)
    }

    companion object {
        private const val OAUTH_AUTHORIZATION_REQUEST_COOKIE_NAME = "_oarc"
        private const val DEFAULT_COOKIE_EXPIRED_AT = 300

        private val objectMapper = ObjectMapper().registerKotlinModule()

        private fun serialize(obj: OAuth2AuthRequestWrapper): String =
            Base64.getUrlEncoder().encodeToString(objectMapper.writeValueAsBytes(obj))

        private fun deserialize(value: String): OAuth2AuthRequestWrapper =
            objectMapper.readValue(Base64.getUrlDecoder().decode(value), OAuth2AuthRequestWrapper::class.java)
    }
}
