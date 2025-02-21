package kr.wooco.woocobe.core.plan.domain.event

data class PlanShareRequestEvent(
    val userId: Long,
    val planId: Long,
    val planTitle: String,
)
