package kr.wooco.woocobe.auth.ui.config

import kr.wooco.woocobe.auth.domain.usecase.ExtractTokenUseCase
import kr.wooco.woocobe.auth.ui.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.NegatedRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val extractTokenUseCase: ExtractTokenUseCase,
) {
    @Bean
    fun tokenFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .securityMatcher(NegatedRequestMatcher(AuthIgnorePaths.ignoreRequestMatcher))
            .cors(Customizer.withDefaults())
            .csrf { it.disable() }
            .logout { it.disable() }
            .formLogin { it.disable() }
            .exceptionHandling { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .addFilterBefore(
                JwtAuthenticationFilter(extractTokenUseCase),
                UsernamePasswordAuthenticationFilter::class.java,
            ).build()
}
