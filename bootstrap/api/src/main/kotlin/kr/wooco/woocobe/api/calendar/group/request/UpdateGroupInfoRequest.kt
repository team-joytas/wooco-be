package kr.wooco.woocobe.api.calendar.group.request

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.UpdateGroupInfoUseCase

data class UpdateGroupInfoRequest(
    val name: String,
) {
    fun toCommand(userId: Long, groupId: Long): UpdateGroupInfoUseCase.Command =
        UpdateGroupInfoUseCase.Command(
            userId = userId,
            groupId = groupId,
            name = name,
        )
}
