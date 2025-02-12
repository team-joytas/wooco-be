package kr.wooco.woocobe.api.common.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

private const val ORDERED = Ordered.HIGHEST_PRECEDENCE

@Order(ORDERED)
@Component
class MdcLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) = MDC.putCloseable(REQUEST_ID, getXRequestId(request)).run {
        filterChain.doFilter(request, response)
    }

    private fun getXRequestId(request: HttpServletRequest): String =
        request.getHeader(X_REQUEST_ID)
            ?: UUID.randomUUID().toString().replace("-", "")

    companion object {
        private const val REQUEST_ID = "request_id"
        private const val X_REQUEST_ID = "X-Request-ID"
    }
}
