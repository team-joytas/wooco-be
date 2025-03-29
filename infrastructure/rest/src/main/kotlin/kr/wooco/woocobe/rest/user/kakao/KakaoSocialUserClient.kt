package kr.wooco.woocobe.rest.user.kakao

import kr.wooco.woocobe.core.user.domain.vo.SocialType
import kr.wooco.woocobe.rest.user.SocialUserClient
import org.springframework.stereotype.Component

@Component
class KakaoSocialUserClient(
    private val kakaoSocialApiClient: KakaoSocialApiClient,
) : SocialUserClient {
    override fun supportType(socialType: SocialType): Boolean = socialType == SocialType.KAKAO

    override fun revokeSocialUser(accessToken: String) {
        val bearerHeader = "Bearer $accessToken"
        kakaoSocialApiClient.revokeSocialUser(bearerHeader)
    }
}
