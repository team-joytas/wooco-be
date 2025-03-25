package kr.wooco.woocobe.rest.auth.kakao

import kr.wooco.woocobe.rest.auth.kakao.response.KakaoSocialTokenResponse
import kr.wooco.woocobe.rest.auth.kakao.response.KakaoSocialUserResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

interface KakaoSocialApiClient {
    @PostExchange(url = KAKAO_TOKEN_URL, contentType = APPLICATION_FORM_URLENCODED_VALUE)
    fun fetchSocialToken(
        @RequestParam params: MultiValueMap<String, String>,
    ): KakaoSocialTokenResponse

    @GetExchange(url = KAKAO_RESOURCE_URL)
    fun fetchSocialUser(
        @RequestHeader(AUTHORIZATION) bearerHeader: String,
    ): KakaoSocialUserResponse

    @PostExchange(url = KAKAO_REVOKE_URL, contentType = APPLICATION_FORM_URLENCODED_VALUE)
    fun revokeSocialUser(
        @RequestHeader(AUTHORIZATION) bearerHeader: String,
    )

    companion object {
        private const val KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token"
        private const val KAKAO_REVOKE_URL = "https://kapi.kakao.com/v1/user/unlink"
        private const val KAKAO_RESOURCE_URL = "https://kapi.kakao.com/v2/user/me"
    }
}
