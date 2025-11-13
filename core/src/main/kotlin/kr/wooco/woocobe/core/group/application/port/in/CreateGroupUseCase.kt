package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.domain.command.CreateGroupCommand
import kr.wooco.woocobe.core.group.domain.entity.Group

fun interface CreateGroupUseCase {
    data class Command(
        val userId: Long,
        val name: String,
    ) {
        fun toCreateCommand(): CreateGroupCommand =
            CreateGroupCommand(
                userId = userId,
                name = Group.Name(name),
            )
    }

    fun createGroup(command: CreateGroupUseCase.Command): Long
}
