package kr.wooco.woocobe.common.config

import kr.wooco.woocobe.auth.infrastructure.token.JWTProvider
import kr.wooco.woocobe.common.security.AuthIgnorePaths
import kr.wooco.woocobe.common.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Order(1)
    @Bean
    fun jwtFilterChain(
        http: HttpSecurity,
        jwtProvider: JWTProvider,
        handlerExceptionResolver: HandlerExceptionResolver,
    ): SecurityFilterChain =
        http
            .securityMatcher("/**")
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .logout { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            ).exceptionHandling {
                it
                    .accessDeniedHandler { request, response, e ->
                        handlerExceptionResolver.resolveException(request, response, null, e)
                    }.authenticationEntryPoint { request, response, e ->
                        handlerExceptionResolver.resolveException(request, response, null, e)
                    }
            }.build()

    @Order(0)
    @Bean
    fun ignoreFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher(AuthIgnorePaths.ignoreRequestMatcher)
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .build()
}
