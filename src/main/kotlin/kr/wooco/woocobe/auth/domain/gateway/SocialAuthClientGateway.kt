package kr.wooco.woocobe.auth.domain.gateway

import kr.wooco.woocobe.auth.domain.model.SocialAuth

interface SocialAuthClientGateway {
    fun getSocialAuthInfo(socialToken: String): SocialAuth
}
