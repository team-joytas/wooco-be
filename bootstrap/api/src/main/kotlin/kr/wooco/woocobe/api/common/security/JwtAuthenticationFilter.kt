package kr.wooco.woocobe.api.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.api.common.utils.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        extractAccessToken(request)?.let { processAuthentication(it) }
        filterChain.doFilter(request, response)
    }

    private fun extractAccessToken(request: HttpServletRequest): String? =
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            ?.takeIf { it.startsWith(BEARER_PREFIX) }
            ?.substring(BEARER_PREFIX.length)

    private fun processAuthentication(accessToken: String) =
        runCatching {
            val userId = JwtUtils.extractUserIdInAccessToken(accessToken)
            return@runCatching UsernamePasswordAuthenticationToken(userId, null, emptyList())
        }.onSuccess {
            SecurityContextHolder.getContext().apply {
                this.authentication = it
            }
        }

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
