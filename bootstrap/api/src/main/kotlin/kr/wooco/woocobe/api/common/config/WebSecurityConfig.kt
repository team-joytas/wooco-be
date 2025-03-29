package kr.wooco.woocobe.api.common.config

import kr.wooco.woocobe.api.common.security.AuthIgnorePaths
import kr.wooco.woocobe.api.common.security.CookieOAuthRequestRepository
import kr.wooco.woocobe.api.common.security.CustomOAuth2UserService
import kr.wooco.woocobe.api.common.security.JwtAuthenticationFilter
import kr.wooco.woocobe.api.common.security.OAuthFailureHandler
import kr.wooco.woocobe.api.common.security.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
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
            .authorizeHttpRequests {
                it
                    .requestMatchers(AuthIgnorePaths.ignoreRequestMatcher)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.addFilterBefore(
                JwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java,
            ).oauth2Login {
                it
                    .authorizationEndpoint { config ->
                        config.baseUri("/api/v1/oauth2/authorization")
                    }.redirectionEndpoint { config ->
                        config.baseUri("/api/v1/oauth2/*/login")
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
    }
}
