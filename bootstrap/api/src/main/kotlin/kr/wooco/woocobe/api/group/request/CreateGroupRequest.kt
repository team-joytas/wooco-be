package kr.wooco.woocobe.api.group.request

import kr.wooco.woocobe.core.group.application.port.`in`.CreateGroupUseCase

data class CreateGroupRequest(
    val name: String,
) {
    fun toCommand(userId: Long): CreateGroupUseCase.Command =
        CreateGroupUseCase.Command(
            userId = userId,
            name = name,
        )
}
