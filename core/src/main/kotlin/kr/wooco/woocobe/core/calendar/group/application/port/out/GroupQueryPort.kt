package kr.wooco.woocobe.core.calendar.group.application.port.out

import kr.wooco.woocobe.core.calendar.group.application.port.out.dto.GroupView

interface GroupQueryPort {

    fun getViewById(groupId: Long): GroupView

    fun getViewAllByUserId(userId: Long): List<GroupView>

    fun getViewAllByIds(groupIds: List<Long>): List<GroupView>
}
