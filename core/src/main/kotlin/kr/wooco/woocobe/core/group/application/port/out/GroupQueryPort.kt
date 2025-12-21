package kr.wooco.woocobe.core.group.application.port.out

import kr.wooco.woocobe.core.group.application.port.out.dto.GroupView

interface GroupQueryPort {

    fun getViewByIdWithActive(groupId: Long): GroupView

    fun getViewAllByUserIdWithActive(userId: Long): List<GroupView>

    fun getViewAllByIdsWithActive(groupIds: List<Long>): List<GroupView>
}
