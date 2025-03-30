package kr.wooco.woocobe.api.common.security

import kr.wooco.woocobe.core.user.application.port.`in`.SocialLoginUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class CustomOAuth2UserService(
    private val socialLoginUseCase: SocialLoginUseCase,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val command = SocialLoginUseCase.Command(
            socialId = oAuth2User.name,
            socialType = userRequest.clientRegistration.registrationId,
        )
        val userId = socialLoginUseCase.socialLogin(command)

        return CustomOAuth2User(
            userId = userId,
            socialToken = userRequest.accessToken.tokenValue,
            oAuth2User = oAuth2User,
        )
    }
}
