package kr.wooco.woocobe.core.calendar.group.application.port.out

import kr.wooco.woocobe.core.calendar.group.domain.entity.Group

interface GroupCommandPort {

    fun save(group: Group): Long

    fun getById(groupId: Long): Group

    fun getByInviteCode(inviteToken: String): Group
}
