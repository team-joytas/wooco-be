package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.GenerateInviteCodeCommand

interface GenerateInviteCodeUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
    ) {
        fun toGenerateInviteCodeCommand(): GenerateInviteCodeCommand =
            GenerateInviteCodeCommand(
                userId = userId,
                groupId = groupId,
            )
    }

    fun generateInviteCode(command: Command): String
}
