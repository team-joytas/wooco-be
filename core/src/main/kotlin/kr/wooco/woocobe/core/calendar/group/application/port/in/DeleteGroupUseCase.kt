package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.DeleteGroupCommand

interface DeleteGroupUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
    ) {
        fun toDeleteCommand(): DeleteGroupCommand =
            DeleteGroupCommand(
                userId = userId,
                groupId = groupId,
            )
    }

    fun deleteGroup(command: Command): Long
}
