package kr.wooco.woocobe.core.group.application.port.out

import kr.wooco.woocobe.core.group.application.port.out.dto.GroupView
import kr.wooco.woocobe.core.group.domain.entity.Group

interface GroupQueryPort {

    fun getViewByIdAndStatus(groupId: Long, status: Group.Status): GroupView

    fun getViewAllByUserIdAndStatus(userId: Long, status: Group.Status): List<GroupView>
}
