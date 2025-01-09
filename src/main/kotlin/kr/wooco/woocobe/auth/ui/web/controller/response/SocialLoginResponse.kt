package kr.wooco.woocobe.auth.ui.web.controller.response

import kr.wooco.woocobe.auth.domain.usecase.SocialLoginOutput

data class SocialLoginResponse(
    val accessToken: String,
) {
    companion object {
        fun from(socialLoginOutput: SocialLoginOutput): SocialLoginResponse =
            SocialLoginResponse(
                accessToken = socialLoginOutput.accessToken,
            )
    }
}
