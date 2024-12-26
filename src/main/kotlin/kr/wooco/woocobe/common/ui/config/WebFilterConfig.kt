package kr.wooco.woocobe.common.ui.config

import kr.wooco.woocobe.common.ui.filter.ExceptionResolverFilter
import kr.wooco.woocobe.common.ui.filter.MdcLoggingFilter
import kr.wooco.woocobe.common.ui.filter.ReqResLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class WebFilterConfig {
    @Bean
    fun exceptionResolverFilter(handlerExceptionResolver: HandlerExceptionResolver): FilterRegistrationBean<ExceptionResolverFilter> =
        FilterRegistrationBean<ExceptionResolverFilter>().apply {
            filter = ExceptionResolverFilter(handlerExceptionResolver)
            order = Ordered.HIGHEST_PRECEDENCE
            addUrlPatterns("/*")
        }

    @Bean
    fun mdcLoggingFilter(): FilterRegistrationBean<MdcLoggingFilter> =
        FilterRegistrationBean<MdcLoggingFilter>().apply {
            filter = MdcLoggingFilter()
            order = Ordered.HIGHEST_PRECEDENCE + 1
            addUrlPatterns("/*")
        }

    @Bean
    fun reqResLoggingFilter(): FilterRegistrationBean<ReqResLoggingFilter> =
        FilterRegistrationBean<ReqResLoggingFilter>().apply {
            filter = ReqResLoggingFilter()
            order = Ordered.HIGHEST_PRECEDENCE + 2
            addUrlPatterns("/*")
        }
}
