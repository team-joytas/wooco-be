package kr.wooco.woocobe.core.group.application.port.out

import kr.wooco.woocobe.core.group.domain.entity.Group

interface GroupCommandPort {

    fun save(group: Group): Long

    fun getById(groupId: Long): Group

    fun getByInviteCode(inviteToken: String): Group
}
