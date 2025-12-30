package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.JoinGroupCommand

interface JoinGroupUseCase {
    data class Command(
        val userId: Long,
        val inviteCode: String,
    ) {
        fun toJoinCommand(): JoinGroupCommand =
            JoinGroupCommand(
                userId = userId,
                inviteCode = inviteCode,
            )
    }

    fun joinGroup(command: Command): Long
}
