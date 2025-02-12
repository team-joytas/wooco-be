package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity
import org.springframework.stereotype.Component

@Component
internal class PlanPlacePersistenceMapper {
    fun toDomain(planPlaceJpaEntity: PlanPlaceJpaEntity): PlanPlace =
        PlanPlace(
            order = planPlaceJpaEntity.order,
            placeId = planPlaceJpaEntity.placeId,
        )

    fun toEntity(
        planJpaEntity: PlanJpaEntity,
        planPlace: PlanPlace,
    ): PlanPlaceJpaEntity =
        PlanPlaceJpaEntity(
            order = planPlace.order,
            planId = planJpaEntity.id,
            placeId = planPlace.placeId,
        )
}
