package kr.wooco.woocobe.core.group.domain.command

data class GenerateInviteCodeCommand(
    val userId: Long,
    val groupId: Long,
)
