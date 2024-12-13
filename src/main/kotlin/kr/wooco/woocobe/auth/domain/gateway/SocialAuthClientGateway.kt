package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.SocialAuthInfo

interface SocialAuthClientGateway {
    fun getSocialAuthInfo(socialToken: String): SocialAuthInfo
}
