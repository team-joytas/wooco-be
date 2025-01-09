package kr.wooco.woocobe.common.ui.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!prod")
@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        val component = Components().addSecuritySchemes(
            JWT_AUTH,
            SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(BEARER_PREFIX).bearerFormat(JWT_AUTH),
        )
        return OpenAPI().components(component)
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver = ModelResolver(objectMapper)

    @Bean
    fun operationCustomizer(): OperationCustomizer =
        OperationCustomizer { operation, _ ->
            operation.responses
                .forEach { (_, response) ->
                    response.content?.forEach { (_, mediaType) ->
                        mediaType.schema = wrapApiResponseSchema(mediaType.schema)
                    }
                }
            operation
        }

    private fun wrapApiResponseSchema(originSchema: Schema<Any>): Schema<*> =
        Schema<Any>().apply {
            addProperty("path", Schema<String>().example("/api/example"))
            addProperty(
                "results",
                when (originSchema.`$ref`?.contains("Unit")) {
                    true -> Schema<String>().example("null")
                    else -> originSchema
                },
            )
            addProperty("timestamp", Schema<Long>().example(System.currentTimeMillis()))
        }

    companion object {
        private const val JWT_AUTH = "JWT"
        private const val BEARER_PREFIX = "Bearer"
    }
}
