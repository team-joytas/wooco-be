package kr.wooco.woocobe.plan.domain.model

import java.time.LocalDate

// TODO: 장소 정보 추가
class Plan(
    val id: Long,
    val userId: Long,
    val regionInfo: PlanRegionInfo,
    val visitDate: LocalDate,
) {
    companion object {
        fun register(
            planId: Long,
            userId: Long,
            regionInfo: PlanRegionInfo,
            visitDate: LocalDate,
        ): Plan =
            Plan(
                id = planId,
                userId = userId,
                regionInfo = regionInfo,
                visitDate = visitDate,
            )
    }
}
