package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.core.plan.domain.vo.PlanRegion
import kr.wooco.woocobe.core.plan.domain.vo.PlanStatus
import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity

internal object PlanJpaMapper {
    fun toDomainEntity(
        planJpaEntity: PlanJpaEntity,
        planPlaceJpaEntities: List<PlanPlaceJpaEntity>,
    ): Plan =
        Plan(
            id = planJpaEntity.id,
            userId = planJpaEntity.userId,
            title = planJpaEntity.title,
            contents = planJpaEntity.contents,
            region = PlanRegion(
                primaryRegion = planJpaEntity.primaryRegion,
                secondaryRegion = planJpaEntity.secondaryRegion,
            ),
            visitDate = planJpaEntity.visitDate,
            places = planPlaceJpaEntities.map { PlanPlace(order = it.order, placeId = it.placeId) },
            status = PlanStatus(planJpaEntity.status),
        )

    fun toJpaEntity(plan: Plan): PlanJpaEntity =
        PlanJpaEntity(
            id = plan.id,
            userId = plan.userId,
            title = plan.title,
            contents = plan.contents,
            primaryRegion = plan.region.primaryRegion,
            secondaryRegion = plan.region.secondaryRegion,
            visitDate = plan.visitDate,
            status = plan.status.name,
        )

    // TODO: 추후 분리 고려
    fun toPlanPlaceJpaEntity(
        planId: Long,
        planPlace: PlanPlace,
    ): PlanPlaceJpaEntity =
        PlanPlaceJpaEntity(
            order = planPlace.order,
            planId = planId,
            placeId = planPlace.placeId,
        )
}
