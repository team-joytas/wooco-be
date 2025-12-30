package kr.wooco.woocobe.core.calendar.group.domain.command

data class LeaveGroupCommand(
    val userId: Long,
    val groupId: Long,
)
