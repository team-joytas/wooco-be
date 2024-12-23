package kr.wooco.woocobe.auth.infrastructure.client

import kr.wooco.woocobe.auth.infrastructure.client.dto.KakaoOAuthInfoResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI

@Component
class KakaoOAuthRestClient(
    private val restClient: RestClient,
) {
    fun getKakaoOAuthInfo(token: String): KakaoOAuthInfoResponse =
        restClient
            .get()
            .apply {
                uri(URI.create(OAUTH_INFO_URI))
                accept(MediaType.APPLICATION_JSON)
                header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }.retrieve()
            .toEntity(KakaoOAuthInfoResponse::class.java)
            .body
            ?: throw RuntimeException()

    companion object {
        private const val OAUTH_INFO_URI = "https://kapi.kakao.com/v2/user/me"
    }
}
