package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.group.domain.query.ReadGroupQuery

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

    fun readGroup(query: ReadGroupUseCase.Query): GroupResult

}
