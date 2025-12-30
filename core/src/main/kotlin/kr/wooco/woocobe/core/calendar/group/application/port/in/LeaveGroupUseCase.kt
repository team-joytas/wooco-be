package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.LeaveGroupCommand

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

    fun leaveGroup(command: Command): Long

}
