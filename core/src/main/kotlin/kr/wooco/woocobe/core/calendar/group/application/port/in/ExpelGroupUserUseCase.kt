package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.domain.command.ExpelGroupUserCommand

interface ExpelGroupUserUseCase {
    data class Command(
        val userId: Long,
        val groupId: Long,
        val targetId: Long,
    ) {
        fun toExpelGroupUserCommand(): ExpelGroupUserCommand =
            ExpelGroupUserCommand(
                userId = userId,
                groupId = groupId,
                targetId = targetId,
            )
    }

    fun expelGroupUser(command: Command): Long
}
