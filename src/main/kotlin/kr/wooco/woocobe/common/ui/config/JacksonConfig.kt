package kr.wooco.woocobe.common.ui.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.math.abs
import kotlin.math.pow

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapper(): ObjectMapper =
        jacksonObjectMapper().apply {
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            registerModules(JavaTimeModule(), optimizedStringSerializerModule())
        }

    private fun optimizedStringSerializerModule(): SimpleModule =
        SimpleModule().apply {
            addSerializer(Long::class.java, OptimizedLongSerializer)
        }

    data object OptimizedLongSerializer : JsonSerializer<Long>() {
        override fun serialize(
            value: Long,
            gen: JsonGenerator,
            serializers: SerializerProvider,
        ) {
            if (abs(value) > THRESHOLD) {
                gen.writeString(value.toString())
            } else {
                gen.writeNumber(value)
            }
        }
    }

    companion object {
        private val THRESHOLD = 2.0.pow(53.0)
    }
}
