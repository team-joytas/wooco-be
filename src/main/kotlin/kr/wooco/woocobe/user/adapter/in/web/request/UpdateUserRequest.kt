package kr.wooco.woocobe.user.adapter.`in`.web.request

import kr.wooco.woocobe.user.application.port.`in`.UpdateUserProfileUseCase

data class UpdateUserRequest(
    val name: String,
    val profileUrl: String,
    val description: String,
) {
    fun toCommand(userId: Long): UpdateUserProfileUseCase.Command =
        UpdateUserProfileUseCase.Command(
            userId = userId,
            name = name,
            profileUrl = profileUrl,
            description = description,
        )
}
