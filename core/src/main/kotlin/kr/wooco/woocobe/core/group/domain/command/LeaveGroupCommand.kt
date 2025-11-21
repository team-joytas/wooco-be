package kr.wooco.woocobe.core.group.domain.command

data class LeaveGroupCommand(
    val userId: Long,
    val groupId: Long,
)
