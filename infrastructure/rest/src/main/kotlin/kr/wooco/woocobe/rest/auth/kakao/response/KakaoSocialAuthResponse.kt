package kr.wooco.woocobe.rest.auth.kakao.response

import kr.wooco.woocobe.core.auth.domain.vo.SocialInfo
import kr.wooco.woocobe.core.auth.domain.vo.SocialType
import kr.wooco.woocobe.rest.auth.SocialAuthResponse

data class KakaoSocialAuthResponse(
    val id: String,
) : SocialAuthResponse {
    override fun toDomain(): SocialInfo =
        SocialInfo(
            socialId = id,
            socialType = SocialType.KAKAO,
        )
}
