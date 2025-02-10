package kr.wooco.woocobe.auth.adapter.`in`.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.application.port.`in`.ExtractTokenUseCase
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val extractTokenUseCase: ExtractTokenUseCase,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        extractAccessToken(request)?.let {
            processAuthentication(it)
        }
        filterChain.doFilter(request, response)
    }

    private fun extractAccessToken(request: HttpServletRequest): String? =
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith(BEARER_PREFIX) }
            ?.substring(BEARER_PREFIX.length)

    private fun processAuthentication(accessToken: String) {
        val userId = extractTokenUseCase.extractToken(accessToken)
        val authentication = UsernamePasswordAuthenticationToken(userId, null, emptyList())
        SecurityContextHolder.getContext().apply {
            this.authentication = authentication
        }
    }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
