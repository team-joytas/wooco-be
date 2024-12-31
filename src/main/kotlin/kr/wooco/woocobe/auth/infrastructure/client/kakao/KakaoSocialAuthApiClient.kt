package kr.wooco.woocobe.auth.infrastructure.client.kakao

import kr.wooco.woocobe.auth.infrastructure.client.kakao.response.KakaoSocialAuthResponse
import kr.wooco.woocobe.auth.infrastructure.client.kakao.response.KakaoSocialTokenResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

interface KakaoSocialAuthApiClient {
    @PostExchange(url = KAKAO_TOKEN_URL, contentType = APPLICATION_FORM_URLENCODED_VALUE)
    fun fetchSocialToken(
        @RequestParam params: MultiValueMap<String, String>,
    ): KakaoSocialTokenResponse

    @GetExchange(url = KAKAO_RESOURCE_URL)
    fun fetchSocialAuth(
        @RequestHeader(AUTHORIZATION) authorization: String,
    ): KakaoSocialAuthResponse

    companion object {
        private const val KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token"
        private const val KAKAO_RESOURCE_URL = "https://kapi.kakao.com/v2/user/me"
    }
}
