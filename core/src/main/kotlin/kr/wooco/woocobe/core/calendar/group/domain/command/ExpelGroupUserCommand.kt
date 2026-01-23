package kr.wooco.woocobe.core.calendar.group.domain.command

data class ExpelGroupUserCommand(
    val userId: Long,
    val groupId: Long,
    val targetId : Long,
)
