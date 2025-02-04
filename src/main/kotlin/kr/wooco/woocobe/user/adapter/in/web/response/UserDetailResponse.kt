package kr.wooco.woocobe.user.adapter.`in`.web.response

import kr.wooco.woocobe.user.application.port.`in`.results.UserResult

data class UserDetailResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val onBoarding: Boolean,
) {
    companion object {
        fun from(userResult: UserResult): UserDetailResponse =
            UserDetailResponse(
                userId = userResult.id,
                name = userResult.name,
                profileUrl = userResult.profileUrl,
                description = userResult.description,
                onBoarding = userResult.name.isBlank(),
            )
    }
}
