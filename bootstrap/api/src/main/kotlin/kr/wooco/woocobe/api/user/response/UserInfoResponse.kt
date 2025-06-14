package kr.wooco.woocobe.api.user.response

import kr.wooco.woocobe.core.user.application.port.`in`.results.UserInfoResult

data class UserInfoResponse(
    val userId: Long,
    val name: String,
    val profileUrl: String,
    val description: String,
    val status: String,
    val onBoarding: Boolean,
) {
    companion object {
        private const val ONBOARDING = "ONBOARDING"

        fun from(userInfoResult: UserInfoResult): UserInfoResponse =
            UserInfoResponse(
                userId = userInfoResult.id,
                name = userInfoResult.name,
                profileUrl = userInfoResult.profileUrl,
                description = userInfoResult.description,
                status = userInfoResult.status,
                onBoarding = userInfoResult.status == ONBOARDING,
            )
    }
}
