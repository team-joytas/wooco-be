package kr.wooco.woocobe.core.plan.domain.vo

enum class PlanStatus {
    ACTIVE,
    DELETED,
    ;

    companion object {
        operator fun invoke(value: String) = PlanStatus.valueOf(value)
    }
}
