package kr.wooco.woocobe.core.calendar.group.domain.command

data class JoinGroupCommand(
    val userId: Long,
    val inviteCode: String,
)
