package kr.wooco.woocobe.core.calendar.group.domain.command

data class GenerateInviteCodeCommand(
    val userId: Long,
    val groupId: Long,
)
