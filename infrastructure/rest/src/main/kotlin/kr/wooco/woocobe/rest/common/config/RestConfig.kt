package kr.wooco.woocobe.rest.common.config

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.rest.auth.kakao.KakaoSocialAuthApiClient
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
@ComponentScan(basePackages = ["kr.wooco.woocobe.rest"])
@ConfigurationPropertiesScan(basePackages = ["kr.wooco.woocobe.rest"])
class RestConfig {
    @Bean
    fun kakaoSocialAuthApiClient(): KakaoSocialAuthApiClient =
        HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(generateRestClient()))
            .build()
            .createClient(KakaoSocialAuthApiClient::class.java)

    private fun generateRestClient(): RestClient =
        RestClient
            .builder()
            .defaultStatusHandler(
                { status: HttpStatusCode -> status.is4xxClientError },
                { _, response ->
                    log.error { "CLIENT 4XX ERROR\n| >> STATUS=${response.statusCode}\n| >> MESSAGE=${response.readBody()}" }
                },
            ).defaultStatusHandler(
                { status: HttpStatusCode -> status.is5xxServerError },
                { _, response ->
                    log.error { "CLIENT 5XX ERROR\n| >> STATUS=${response.statusCode}\n| >> MESSAGE=${response.readBody()}" }
                },
            ).build()

    companion object {
        private val log = KotlinLogging.logger {}

        private fun ClientHttpResponse.readBody(): String = String(this.body.readAllBytes())
    }
}
