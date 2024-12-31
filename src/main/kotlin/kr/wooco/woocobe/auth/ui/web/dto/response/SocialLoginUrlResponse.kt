package kr.wooco.woocobe.auth.ui.web.dto.response

import kr.wooco.woocobe.auth.domain.usecase.GetSocialLoginUrlOutput

data class SocialLoginUrlResponse(
    val url: String,
) {
    companion object {
        fun from(results: GetSocialLoginUrlOutput): SocialLoginUrlResponse =
            SocialLoginUrlResponse(
                url = results.loginUrl,
            )
    }
}
