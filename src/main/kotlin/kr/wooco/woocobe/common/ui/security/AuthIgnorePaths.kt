package kr.wooco.woocobe.common.ui.security

import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

object AuthIgnorePaths {
    val ignoreRequestMatcher: RequestMatcher = initIgnorePaths()

    private fun initIgnorePaths(): OrRequestMatcher =
        OrRequestMatcher(
            append(path = "/actuator/health", method = HttpMethod.GET),
            append(path = "/api/v1/auth/login", method = HttpMethod.POST),
            append(path = "/api/v1/auth/reissue", method = HttpMethod.POST),
        )

    private fun append(
        path: String,
        method: HttpMethod,
    ): AntPathRequestMatcher = AntPathRequestMatcher(path, method.name())
}
