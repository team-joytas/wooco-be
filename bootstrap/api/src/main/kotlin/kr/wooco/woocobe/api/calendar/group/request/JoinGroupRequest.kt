package kr.wooco.woocobe.api.calendar.group.request

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.JoinGroupUseCase

data class JoinGroupRequest(
    val inviteCode: String,
) {
    fun toCommand(userId: Long): JoinGroupUseCase.Command =
        JoinGroupUseCase.Command(
            userId = userId,
            inviteCode = inviteCode,
        )
}
