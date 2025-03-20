package kr.wooco.woocobe.mysql.plan

import kr.wooco.woocobe.core.plan.domain.entity.Plan
import kr.wooco.woocobe.core.plan.domain.vo.PlanPlace
import kr.wooco.woocobe.core.plan.domain.vo.PlanRegion
import kr.wooco.woocobe.mysql.plan.entity.PlanJpaEntity
import kr.wooco.woocobe.mysql.plan.entity.PlanPlaceJpaEntity

internal object PlanPersistenceMapper {
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
        )
}
