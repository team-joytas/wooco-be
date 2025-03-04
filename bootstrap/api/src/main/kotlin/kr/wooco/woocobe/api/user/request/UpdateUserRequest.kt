package kr.wooco.woocobe.api.user.request

import kr.wooco.woocobe.core.user.application.port.`in`.UpdateUserProfileUseCase

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
