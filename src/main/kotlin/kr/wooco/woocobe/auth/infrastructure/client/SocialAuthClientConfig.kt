package kr.wooco.woocobe.auth.infrastructure.client

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.wooco.woocobe.auth.infrastructure.client.kakao.KakaoSocialAuthApiClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class SocialAuthClientConfig {
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
                    log.error { "OAUTH CLIENT 4XX ERROR\n| >> STATUS=${response.statusCode}\n| >> MESSAGE=${response.readBody()}" }
                },
            ).defaultStatusHandler(
                { status: HttpStatusCode -> status.is5xxServerError },
                { _, response ->
                    log.error { "OAUTH CLIENT 5XX ERROR\n| >> STATUS=${response.statusCode}\n| >> MESSAGE=${response.readBody()}" }
                },
            ).build()

    companion object {
        private val log = KotlinLogging.logger {}

        private fun ClientHttpResponse.readBody(): String = String(this.body.readAllBytes())
    }
}
