package kr.wooco.woocobe.common.utils

import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie

private val properties: CookieProperties by lazy {
    SpringContextLoader.getBean(CookieProperties::class.java)
}

fun HttpServletResponse.addCookie(
    name: String,
    value: String = "",
    maxAge: Long = properties.maxAge,
) = this.setHeader(HttpHeaders.SET_COOKIE, makeCookie(name, value, maxAge))

fun HttpServletResponse.deleteCookie(name: String) = this.setHeader(HttpHeaders.SET_COOKIE, makeCookie(name, "", 0))

private fun makeCookie(
    name: String,
    value: String,
    maxAge: Long,
): String =
    ResponseCookie
        .from(name, value)
        .maxAge(maxAge)
        .path(properties.path)
        .domain(properties.domain)
        .sameSite(properties.sameSite)
        .secure(properties.secure)
        .httpOnly(properties.httpOnly)
        .build()
        .toString()

@ConfigurationProperties(prefix = "spring.cookie")
data class CookieProperties(
    val path: String,
    val maxAge: Long,
    val sameSite: String,
    val domain: String,
    val secure: Boolean,
    val httpOnly: Boolean,
)
