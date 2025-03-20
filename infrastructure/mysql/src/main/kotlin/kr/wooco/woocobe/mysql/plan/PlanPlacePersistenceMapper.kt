package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity

internal object PlanPlacePersistenceMapper {
    fun toJpaEntity(
        planId: Long,
        planPlace: PlanPlace,
    ): PlanPlaceJpaEntity =
        PlanPlaceJpaEntity(
            order = planPlace.order,
            planId = planId,
            placeId = planPlace.placeId,
        )
}
