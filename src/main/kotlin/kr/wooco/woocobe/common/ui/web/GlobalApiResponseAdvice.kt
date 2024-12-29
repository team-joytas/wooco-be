package kr.wooco.woocobe.common.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

internal data class ApiResponse(
    val path: String,
    val results: Any? = null,
    val timestamp: Long = System.currentTimeMillis(),
)

@RestControllerAdvice
class GlobalApiResponseAdvice(
    private val objectMapper: ObjectMapper,
) : ResponseBodyAdvice<Any> {
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>,
    ): Boolean = true

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse,
    ): Any? {
        val path = request.uri.path

        return when {
            path.startsWith(API_PREFIX).not() -> body

            body is String -> {
                response.headers.contentType = MediaType.APPLICATION_JSON
                objectMapper.writeValueAsString(ApiResponse(path = path, results = body))
            }

            else -> ApiResponse(path = path, results = body)
        }
    }

    companion object {
        private const val API_PREFIX = "/api/"
    }
}
