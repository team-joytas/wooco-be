package kr.wooco.woocobe.auth.infrastructure.client.dto

import kr.wooco.woocobe.auth.domain.model.SocialAuthInfo

data class KakaoOAuthInfoResponse(
    val id: String,
) {
    private val socialType: String = "kakao"

    fun toDomain(): SocialAuthInfo =
        SocialAuthInfo.register(
            socialId = id,
            socialType = socialType,
        )
}
