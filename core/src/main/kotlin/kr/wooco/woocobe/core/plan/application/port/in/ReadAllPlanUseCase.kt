package kr.wooco.woocobe.core.plan.application.port.`in`

import kr.wooco.woocobe.core.plan.application.port.`in`.results.PlanResult

fun interface ReadAllPlanUseCase {
    data class Query(
        val userId: Long,
    )

    fun readAllPlan(query: Query): List<PlanResult>
}
