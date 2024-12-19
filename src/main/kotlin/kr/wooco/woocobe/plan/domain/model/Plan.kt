package kr.wooco.woocobe.plan.domain.model

import java.time.LocalDate

class Plan(
    val id: Long,
    val userId: Long,
    var region: PlanRegion,
    var visitDate: LocalDate,
) {
    fun isWriter(targetId: Long): Boolean = userId == targetId

    fun update(
        region: PlanRegion,
        visitDate: LocalDate,
    ) = apply {
        this.region = region
        this.visitDate = visitDate
    }

    companion object {
        fun register(
            userId: Long,
            region: PlanRegion,
            visitDate: LocalDate,
        ): Plan =
            Plan(
                id = 0L,
                userId = userId,
                region = region,
                visitDate = visitDate,
            )
    }
}
