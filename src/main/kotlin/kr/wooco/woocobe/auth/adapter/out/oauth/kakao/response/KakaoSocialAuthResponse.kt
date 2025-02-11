package kr.wooco.woocobe.auth.adapter.out.oauth.kakao.response

import kr.wooco.woocobe.auth.adapter.out.oauth.config.SocialAuthResponse
import kr.wooco.woocobe.auth.domain.vo.SocialInfo
import kr.wooco.woocobe.auth.domain.vo.SocialType

data class KakaoSocialAuthResponse(
    val id: String,
) : SocialAuthResponse {
    override fun toDomain(): SocialInfo =
        SocialInfo(
            socialId = id,
            socialType = SocialType.KAKAO,
        )
}
