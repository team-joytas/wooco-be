package kr.wooco.woocobe.plan.adapter.out.persistence

import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanJpaEntity
import kr.wooco.woocobe.plan.adapter.out.persistence.entity.PlanPlaceJpaEntity
import kr.wooco.woocobe.plan.domain.entity.PlanPlace
import org.springframework.stereotype.Component

@Component
class PlanPlacePersistenceMapper {
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
