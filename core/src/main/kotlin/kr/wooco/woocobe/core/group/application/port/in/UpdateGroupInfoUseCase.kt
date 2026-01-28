package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.domain.command.UpdateGroupInfoCommand
import kr.wooco.woocobe.core.group.domain.entity.Group.Name

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
                name = Name(name),
            )
    }

    fun updateGroupInfo(command: UpdateGroupInfoUseCase.Command): Long
}
