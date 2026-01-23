package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.UpdateGroupInfoCommand
import kr.wooco.woocobe.core.calendar.group.domain.entity.Group

interface UpdateGroupInfoUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
        val name: String,
    ) {
        fun toUpdateGroupInfoCommand(): UpdateGroupInfoCommand =
            UpdateGroupInfoCommand(
                userId = userId,
                groupId = groupId,
                name = Group.Name(name),
            )
    }

    fun updateGroupInfo(command: Command): Long

}
