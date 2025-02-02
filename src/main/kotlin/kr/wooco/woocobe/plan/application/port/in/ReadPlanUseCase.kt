package kr.wooco.woocobe.plan.application.port.`in`

import kr.wooco.woocobe.plan.application.port.`in`.results.PlanResult

fun interface ReadPlanUseCase {
    data class Query(
        val userId: Long,
        val planId: Long,
    )

    fun readPlan(query: Query): PlanResult
}
