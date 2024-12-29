package kr.wooco.woocobe.auth.ui.config

import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

object AuthIgnorePaths {
    val ignoreRequestMatcher: OrRequestMatcher = initIgnorePaths()

    private fun initIgnorePaths(): OrRequestMatcher =
        OrRequestMatcher(
            append(path = "/api-docs/**", method = HttpMethod.GET),
            append(path = "/swagger-ui/**", method = HttpMethod.GET),
            append(path = "/actuator/health", method = HttpMethod.GET),
            append(path = "/api/v1/auth/login", method = HttpMethod.POST),
            append(path = "/api/v1/auth/reissue", method = HttpMethod.POST),
        )

    private fun append(
        path: String,
        method: HttpMethod,
    ): AntPathRequestMatcher = AntPathRequestMatcher(path, method.name())
}
