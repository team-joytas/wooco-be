package kr.wooco.woocobe.core.calendar.group.domain.command

data class DeleteGroupCommand(
    val userId: Long,
    val groupId: Long,
)
