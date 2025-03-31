package kr.wooco.woocobe.api.common.security

import org.springframework.http.HttpMethod
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

object SecurityIgnorePath {
    val ignoreRequestMatcher: OrRequestMatcher = initIgnorePaths()

    private fun initIgnorePaths(): OrRequestMatcher =
        OrRequestMatcher(
            append(path = "/api-docs/**", method = HttpMethod.GET),
            append(path = "/actuator/**", method = HttpMethod.GET),
            append(path = "/swagger-ui/**", method = HttpMethod.GET),
            // course
            append(path = "/api/v1/courses", method = HttpMethod.GET),
            append(path = "/api/v1/courses/{courseId:[0-9]+}", method = HttpMethod.GET),
            append(path = "/api/v1/courses/users/{userId:[0-9]+}", method = HttpMethod.GET),
            append(path = "/api/v1/courses/users/{userId:[0-9]+}/like", method = HttpMethod.GET),
            append(path = "/api/v1/comments/courses/{courseId:[0-9]+}", method = HttpMethod.GET),
            // place
            append(path = "/api/v1/places/{placeId[0-9]+}", method = HttpMethod.GET),
            append(path = "/api/v1/reviews/{placeReviewId:[0-9]+}", method = HttpMethod.GET),
            append(path = "/api/v1/reviews/places/{placeId:[0-9]+}", method = HttpMethod.GET),
            append(path = "/api/v1/reviews/places/users/{userId:[0-9]+}", method = HttpMethod.GET),
            // auth
            append(path = "/api/v1/auth/reissue", method = HttpMethod.POST),
            append(path = "/api/v1/auth/{provider}/social-login", method = HttpMethod.POST),
            append(path = "/api/v1/auth/{provider}/social-login/url", method = HttpMethod.GET),
            // user
            append(path = "/api/v1/users/{userId:[0-9]+}", method = HttpMethod.GET),
        )

    private fun append(
        path: String,
        method: HttpMethod,
    ): AntPathRequestMatcher = AntPathRequestMatcher(path, method.name())
}
