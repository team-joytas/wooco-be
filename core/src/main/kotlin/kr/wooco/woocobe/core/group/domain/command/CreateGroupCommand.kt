package kr.wooco.woocobe.core.group.domain.command

import kr.wooco.woocobe.core.group.domain.entity.Group.Name

data class CreateGroupCommand(
    val userId: Long,
    val name: Name,
)
