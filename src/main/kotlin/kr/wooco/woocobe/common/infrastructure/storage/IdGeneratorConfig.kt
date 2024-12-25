package kr.wooco.woocobe.common.infrastructure.storage

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class IdGeneratorConfig(
    @Value("\${server.node}") private val nodeNumber: Int,
) {
    @PostConstruct
    fun init() {
        node = nodeNumber
    }

    companion object {
        var node: Int = 0
            private set
    }
}
