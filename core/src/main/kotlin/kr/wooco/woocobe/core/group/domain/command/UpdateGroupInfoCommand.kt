package kr.wooco.woocobe.core.group.domain.command

import kr.wooco.woocobe.core.group.domain.entity.Group

data class UpdateGroupInfoCommand(
    val userId: Long,
    val groupId: Long,
    val name: Group.Name,
)
