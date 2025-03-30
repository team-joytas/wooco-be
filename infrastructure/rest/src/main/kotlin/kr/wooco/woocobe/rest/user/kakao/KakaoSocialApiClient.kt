package kr.wooco.woocobe.rest.user.kakao

import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.PostExchange

interface KakaoSocialApiClient {
    @PostExchange(url = KAKAO_REVOKE_URL, contentType = APPLICATION_FORM_URLENCODED_VALUE)
    fun revokeSocialUser(
        @RequestHeader(AUTHORIZATION) bearerHeader: String,
    )

    companion object {
        private const val KAKAO_REVOKE_URL = "https://kapi.kakao.com/v1/user/unlink"
    }
}
