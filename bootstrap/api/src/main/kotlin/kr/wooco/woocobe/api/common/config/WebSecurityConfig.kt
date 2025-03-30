package kr.wooco.woocobe.api.common.config

import kr.wooco.woocobe.api.common.security.CookieOAuthRequestRepository
import kr.wooco.woocobe.api.common.security.CustomOAuth2UserService
import kr.wooco.woocobe.api.common.security.JwtAuthenticationFilter
import kr.wooco.woocobe.api.common.security.OAuthFailureHandler
import kr.wooco.woocobe.api.common.security.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val corsProperties: CorsProperties,
    private val oAuthSuccessHandler: OAuthSuccessHandler,
    private val oAuthFailureHandler: OAuthFailureHandler,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val cookieOAuthRequestRepository: CookieOAuthRequestRepository,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .logout { it.disable() }
            .formLogin { it.disable() }
            .anonymous { it.disable() }
            .exceptionHandling { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .addFilterBefore(
                JwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java,
            ).oauth2Login {
                it
                    .authorizationEndpoint { config ->
                        config.baseUri(AUTHORIZATION_REQUEST_URI)
                    }.redirectionEndpoint { config ->
                        config.baseUri(AUTHORIZATION_RESPONSE_URI)
                    }.authorizationEndpoint { config ->
                        config.authorizationRequestRepository(cookieOAuthRequestRepository)
                    }.userInfoEndpoint { config ->
                        config.userService(customOAuth2UserService)
                    }.successHandler(oAuthSuccessHandler)
                    .failureHandler(oAuthFailureHandler)
            }.build()

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration(
                MATCH_ALL_PATTERN,
                CorsConfiguration().apply {
                    maxAge = corsProperties.maxAge
                    allowedMethods = corsProperties.allowedMethods
                    allowedOrigins = corsProperties.allowedOrigins
                    allowedHeaders = corsProperties.allowedHeaders
                    exposedHeaders = corsProperties.exposedHeaders
                    allowCredentials = corsProperties.allowCredentials
                },
            )
        }

    companion object {
        private const val MATCH_ALL_PATTERN = "/**"
        private const val AUTHORIZATION_REQUEST_URI = "/api/v1/oauth2/authorization"
        private const val AUTHORIZATION_RESPONSE_URI = "/api/v1/oauth2/*/login"
    }
}
