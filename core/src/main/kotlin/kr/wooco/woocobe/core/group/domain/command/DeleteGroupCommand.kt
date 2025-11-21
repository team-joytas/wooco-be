package kr.wooco.woocobe.core.group.domain.command

data class DeleteGroupCommand(
    val userId: Long,
    val groupId: Long,
)
