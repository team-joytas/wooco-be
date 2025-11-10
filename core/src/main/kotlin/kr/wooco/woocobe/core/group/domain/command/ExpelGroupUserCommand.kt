package kr.wooco.woocobe.core.group.domain.command

data class ExpelGroupUserCommand(
    val userId: Long,
    val groupId: Long,
    val targetId : Long,
)
