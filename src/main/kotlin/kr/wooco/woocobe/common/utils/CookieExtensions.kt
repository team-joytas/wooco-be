package kr.wooco.woocobe.common.utils

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.context.properties.ConfigurationProperties

private const val SAME_SITE = "sameSite"

private val properties: CookieProperties by lazy {
    SpringContextLoader.getBean(CookieProperties::class.java)
}

fun HttpServletResponse.addCookie(
    name: String,
    value: String,
    maxAge: Int = properties.maxAge,
) {
    val cookie = generateCookie(name, value, maxAge)
    this.addCookie(cookie)
}

fun HttpServletResponse.deleteCookie(name: String) {
    val cookie = generateCookie(name)
    this.addCookie(cookie)
}

private fun generateCookie(
    name: String,
    value: String = "",
    maxAge: Int = 0,
): Cookie =
    Cookie(name, value).apply {
        setMaxAge(maxAge)
        path = properties.path
        domain = properties.domain
        secure = properties.secure
        isHttpOnly = properties.httpOnly
        setAttribute(SAME_SITE, properties.sameSite)
    }

@ConfigurationProperties(prefix = "spring.cookie")
data class CookieProperties(
    val path: String,
    val maxAge: Int,
    val sameSite: String,
    val domain: String,
    val secure: Boolean,
    val httpOnly: Boolean,
)
