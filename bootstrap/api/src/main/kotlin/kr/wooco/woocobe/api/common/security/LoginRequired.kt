package kr.wooco.woocobe.api.common.security

import org.springframework.security.access.prepost.PreAuthorize

/**
 * 로그인이 필수인 엔드포인트
 */
@PreAuthorize("isAuthenticated()")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class LoginRequired
