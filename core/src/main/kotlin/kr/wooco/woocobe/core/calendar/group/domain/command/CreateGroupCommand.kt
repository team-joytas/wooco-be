package kr.wooco.woocobe.core.calendar.group.domain.command

import kr.wooco.woocobe.core.calendar.group.domain.entity.Group

data class CreateGroupCommand(
    val userId: Long,
    val name: Group.Name,
)
