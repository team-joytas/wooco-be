package kr.wooco.woocobe.core.calendar.group.application.port.`in`

import kr.wooco.woocobe.core.calendar.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.calendar.group.domain.query.ReadGroupQuery

interface ReadGroupUseCase {
    data class Query(
        val userId: Long,
        val groupId: Long,
    ) {
        fun toReadGroupQuery(): ReadGroupQuery =
            ReadGroupQuery(
                userId = userId,
                groupId = groupId,
            )
    }

    fun readGroup(query: Query): GroupResult

}
