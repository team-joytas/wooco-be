package kr.wooco.woocobe.auth.infrastructure.client.kakao

import kr.wooco.woocobe.auth.infrastructure.client.kakao.response.KakaoSocialAuthResponse
import kr.wooco.woocobe.auth.infrastructure.client.kakao.response.KakaoSocialTokenResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

interface KakaoSocialAuthApiClient {
    @PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
    fun fetchSocialToken(
        @RequestParam params: Map<String, String>,
    ): KakaoSocialTokenResponse

    @GetExchange(url = "https://kapi.kakao.com/v2/user/me")
    fun fetchSocialAuth(
        @RequestHeader(AUTHORIZATION) authorization: String,
    ): KakaoSocialAuthResponse
}
