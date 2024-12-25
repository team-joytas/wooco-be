package kr.wooco.woocobe.common.ui.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.infrastructure.token.JWTProvider
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JWTProvider,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        runCatching {
            val accessToken = extractAccessTokenInRequestHeader(request)
            val userId = jwtProvider.extractUserId(accessToken)
            SecurityContextHolder.getContext().apply {
                authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
            }
        }.onFailure {
            SecurityContextHolder.clearContext()
        }.run {
            filterChain.doFilter(request, response)
        }
    }

    private fun extractAccessTokenInRequestHeader(request: HttpServletRequest): String =
        request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
            when (it.startsWith(BEARER_PREFIX)) {
                true -> it.substring(BEARER_PREFIX.length)
                else -> null
            }
        } ?: throw RuntimeException()

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
