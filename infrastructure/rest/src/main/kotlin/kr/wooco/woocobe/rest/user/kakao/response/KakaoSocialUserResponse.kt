package kr.wooco.woocobe.rest.auth.kakao.response

import kr.wooco.woocobe.core.user.application.port.out.dto.SocialUserInfo
import kr.wooco.woocobe.core.user.domain.vo.SocialProvider

data class KakaoSocialUserResponse(
    val id: String,
) {
    fun toDto(refreshToken: String): SocialUserInfo =
        SocialUserInfo(
            identifier = id,
            socialToken = refreshToken,
            provider = SocialProvider.KAKAO,
        )
}
