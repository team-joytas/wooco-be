package kr.wooco.woocobe.common.infrastructure.storage

import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JdslConfig {
    @Bean
    fun jpqlRenderer(): JpqlRenderer = JpqlRenderer()
}
