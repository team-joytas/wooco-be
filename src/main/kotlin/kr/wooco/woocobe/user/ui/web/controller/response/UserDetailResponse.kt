package kr.wooco.woocobe.user.ui.web.controller.response

import kr.wooco.woocobe.user.domain.entity.User

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
                name = user.profile.name,
                profileUrl = user.profile.profileUrl,
                description = user.profile.description,
                onBoarding = user.profile.name.isBlank(),
            )
    }
}
