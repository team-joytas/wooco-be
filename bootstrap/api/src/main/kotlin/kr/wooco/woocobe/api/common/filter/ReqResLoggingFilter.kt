package kr.wooco.woocobe.api.common.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

private const val ORDERED = Ordered.HIGHEST_PRECEDENCE + 1

@Order(ORDERED)
@Component
class ReqResLoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val cachedRequest = ContentCachingRequestWrapper(request)
        val cachedResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(cachedRequest, cachedResponse)

        doLogging(cachedRequest, cachedResponse)
        cachedResponse.copyBodyToResponse()
    }

    private fun doLogging(
        request: ContentCachingRequestWrapper,
        response: ContentCachingResponseWrapper,
    ) {
        val status = response.status
        val method = request.method
        val remoteAddr = request.remoteAddr
        val requestURI = request.requestURI
        val formattedQueryString = getFormattedQueryString(request.queryString)
        val requestBody = getRequestBody(request)
        val responseBody = getResponseBody(response)

        val loggingMessage = buildString {
            append("\n| [REQUEST] ($method) $requestURI$formattedQueryString")
            append("\n| >> STATUS: $status")
            append("\n| >> REMOTE_ADDRESS: $remoteAddr")
            requestBody?.let { append("\n| >> REQUEST_BODY: $it") }
            responseBody?.let { append("\n| >> RESPONSE_BODY: $it") }
        }

        when (status < 500) {
            true -> log.info { loggingMessage }
            else -> log.error { loggingMessage }
        }
    }

    private fun getFormattedQueryString(queryString: String?): String =
        queryString
            ?.takeIf { it.isNotBlank() }
            ?.let { "?$it" }
            ?: ""

    private fun getRequestBody(request: ContentCachingRequestWrapper): String? =
        runCatching {
            ByteArrayInputStream(request.contentAsByteArray)
                .use { inputStream ->
                    StreamUtils
                        .copyToString(inputStream, StandardCharsets.UTF_8)
                        .takeIf { it.isNotEmpty() }
                }
        }.getOrNull()

    private fun getResponseBody(response: ContentCachingResponseWrapper): String? =
        runCatching {
            response.contentInputStream.use { inputStream ->
                StreamUtils
                    .copyToString(inputStream, StandardCharsets.UTF_8)
                    .takeIf { it.isNotEmpty() }
            }
        }.getOrNull()

    companion object {
        private val log = KotlinLogging.logger {}
    }
}
