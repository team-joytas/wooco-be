package kr.wooco.woocobe.auth.ui.web.dto.response

import kr.wooco.woocobe.auth.domain.usecase.ReissueTokenOutput

data class ReissueTokenResponse(
    val accessToken: String,
) {
    companion object {
        fun from(result: ReissueTokenOutput): ReissueTokenResponse =
            ReissueTokenResponse(
                accessToken = result.accessToken,
            )
    }
}
