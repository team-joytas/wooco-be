package kr.wooco.woocobe.plan.infrastructure.storage

import kr.wooco.woocobe.plan.domain.model.PlanPlace
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanJpaEntity
import kr.wooco.woocobe.plan.infrastructure.storage.entity.PlanPlaceJpaEntity
import org.springframework.stereotype.Component

@Component
class PlanPlaceStorageMapper {
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
