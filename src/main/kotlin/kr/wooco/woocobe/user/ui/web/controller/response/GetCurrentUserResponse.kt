package kr.wooco.woocobe.user.ui.web.controller.response

import kr.wooco.woocobe.user.domain.usecase.GetUserOutput

data class GetCurrentUserResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
) {
    companion object {
        fun from(getUserOutput: GetUserOutput): GetCurrentUserResponse =
            with(getUserOutput) {
                GetCurrentUserResponse(
                    userId = user.id,
                    name = user.name,
                    profileUrl = user.profileUrl,
                )
            }
    }
}
