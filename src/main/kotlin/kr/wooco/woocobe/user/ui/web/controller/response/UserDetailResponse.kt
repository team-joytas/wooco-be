package kr.wooco.woocobe.user.ui.web.controller.response

import kr.wooco.woocobe.user.domain.model.User

data class UserDetailResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val onBoarding: Boolean,
) {
    companion object {
        fun from(user: User): UserDetailResponse =
            UserDetailResponse(
                userId = user.id,
                name = user.name,
                profileUrl = user.profileUrl,
                description = user.description,
                onBoarding = user.name.isBlank(),
            )
    }
}
