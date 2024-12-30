package kr.wooco.woocobe.auth.infrastructure.client.kakao.response

import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType
import kr.wooco.woocobe.auth.infrastructure.client.SocialAuthResponse

data class KakaoSocialAuthResponse(
    val id: String,
) : SocialAuthResponse {
    override fun toDomain(): SocialAuth =
        SocialAuth(
            socialId = id,
            socialType = SocialType.KAKAO,
        )
}
