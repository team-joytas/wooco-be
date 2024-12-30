package kr.wooco.woocobe.auth.infrastructure.client.dto

import kr.wooco.woocobe.auth.domain.model.SocialAuth

data class KakaoOAuthInfoResponse(
    val id: String,
) {
    private val socialType: String = "kakao"

    fun toDomain(): SocialAuth =
        SocialAuth.register(
            socialId = id,
            socialType = socialType,
        )
}
