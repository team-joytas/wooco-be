package kr.wooco.woocobe.user.ui.web.controller.response

import kr.wooco.woocobe.user.domain.usecase.GetUserOutput

data class GetCurrentUserResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(getUserOutput: GetUserOutput): GetCurrentUserResponse =
            GetCurrentUserResponse(
                userId = getUserOutput.user.id,
                name = getUserOutput.user.name,
                profileUrl = getUserOutput.user.profileUrl,
            )
    }
}
