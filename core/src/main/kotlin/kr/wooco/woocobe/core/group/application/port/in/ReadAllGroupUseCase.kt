package kr.wooco.woocobe.core.group.application.port.`in`

import kr.wooco.woocobe.core.group.application.port.`in`.results.GroupResult
import kr.wooco.woocobe.core.group.domain.query.ReadAllGroupQuery

interface ReadAllGroupUseCase {
    data class Query(
        val userId: Long,
    ) {
        fun toReadAllGroupQuery(): ReadAllGroupQuery =
            ReadAllGroupQuery(
                userId = userId,
            )
    }

    fun readAllGroup(query: ReadAllGroupUseCase.Query): List<GroupResult>

}
