package kr.wooco.woocobe.user.ui.web.controller.request

import kr.wooco.woocobe.user.domain.usecase.UpdateUserInput

data class UpdateUserRequest(
    val name: String,
    val profileUrl: String,
    val description: String,
) {
    fun toCommand(userId: Long): UpdateUserInput =
        UpdateUserInput(
            userId = userId,
            name = name,
            profileUrl = profileUrl,
            description = description,
        )
}
