package kr.wooco.woocobe.api.calendar.group.request

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.CreateGroupUseCase

data class CreateGroupRequest(
    val name: String,
) {
    fun toCommand(userId: Long): CreateGroupUseCase.Command =
        CreateGroupUseCase.Command(
            userId = userId,
            name = name,
        )
}
