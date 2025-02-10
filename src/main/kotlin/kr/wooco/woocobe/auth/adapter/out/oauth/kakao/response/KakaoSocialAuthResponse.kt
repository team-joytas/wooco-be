package kr.wooco.woocobe.auth.adapter.out.oauth.kakao.response

import kr.wooco.woocobe.auth.adapter.out.oauth.config.SocialAuthResponse
import kr.wooco.woocobe.auth.domain.model.SocialAuth
import kr.wooco.woocobe.auth.domain.model.SocialType

data class KakaoSocialAuthResponse(
    val id: String,
) : SocialAuthResponse {
    override fun toDomain(): SocialAuth =
        SocialAuth(
            socialId = id,
            socialType = SocialType.KAKAO,
        )
}
