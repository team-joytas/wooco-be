package kr.wooco.woocobe.plan.domain.model

class Plan(
    val id: Long,
    val userId: Long,
    var region: PlanRegion,
    var visitDate: PlanDate,
) {
    fun isWriter(targetId: Long): Boolean = userId == targetId

    fun update(
        region: PlanRegion,
        visitDate: PlanDate,
    ) = apply {
        this.region = region
        this.visitDate = visitDate
    }

    companion object {
        fun register(
            userId: Long,
            region: PlanRegion,
            visitDate: PlanDate,
        ): Plan =
            Plan(
                id = 0L,
                userId = userId,
                region = region,
                visitDate = visitDate,
            )
    }
}
