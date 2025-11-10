package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.domain.command.JoinGroupCommand

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

    fun joinGroup(command: JoinGroupUseCase.Command): Long
}
