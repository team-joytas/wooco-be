package kr.wooco.woocobe.core.group.domain.command

data class JoinGroupCommand(
    val userId: Long,
    val inviteCode: String,
)
