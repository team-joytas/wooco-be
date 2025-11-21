package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.domain.command.LeaveGroupCommand

interface LeaveGroupUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
    ) {
        fun toLeaveCommand(): LeaveGroupCommand =
            LeaveGroupCommand(
                userId = userId,
                groupId = groupId,
            )
    }

    fun leaveGroup(command: LeaveGroupUseCase.Command): Long

}
