package kr.wooco.woocobe.auth.ui.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.wooco.woocobe.auth.domain.usecase.ExtractTokenInput
import kr.wooco.woocobe.auth.domain.usecase.ExtractTokenUseCase
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val extractTokenUseCase: ExtractTokenUseCase,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val accessToken = extractAccessTokenInRequestHeader(request)
        val results = extractTokenUseCase.execute(
            ExtractTokenInput(
                accessToken = accessToken,
            ),
        )

        val authentication = UsernamePasswordAuthenticationToken(results.userId, null, emptyList())
        SecurityContextHolder.getContext().apply {
            this.authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun extractAccessTokenInRequestHeader(request: HttpServletRequest): String =
        request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
            when (it.startsWith(BEARER_PREFIX)) {
                true -> it.substring(BEARER_PREFIX.length)
                else -> throw RuntimeException()
            }
        } ?: throw RuntimeException()

    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }
}
