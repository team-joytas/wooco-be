package kr.wooco.woocobe.auth.infrastructure.client

import kr.wooco.woocobe.auth.infrastructure.client.kakao.KakaoSocialAuthApiClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class SocialAuthClientConfig {
    @Bean
    fun kakaoSocialAuthApiClient(): KakaoSocialAuthApiClient =
        HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(RestClient.builder().build()))
            .build()
            .createClient(KakaoSocialAuthApiClient::class.java)
}
