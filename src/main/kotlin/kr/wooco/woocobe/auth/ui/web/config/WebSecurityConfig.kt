package kr.wooco.woocobe.auth.ui.web.config

import kr.wooco.woocobe.auth.ui.web.filter.JwtAuthenticationFilter
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
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
) {
    @Bean
    fun tokenFilterChain(http: HttpSecurity): SecurityFilterChain =
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
            }.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

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
